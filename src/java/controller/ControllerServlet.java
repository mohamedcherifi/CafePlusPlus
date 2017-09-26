/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import entity.Categorie;
import entity.Produit;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import session.CategorieFacade;
import session.OrderManager;
import session.ProduitFacade;
import validate.Validator;

/**
 *
 * @author A.Sashcov
 */
@WebServlet(name = "ControllerServlet",  loadOnStartup = 1, urlPatterns = {"/categorie", "/ajouterPanier", "/visualiserPanier", "/majPanier", "/passerLaCommande", "/acheter", "/selectionnerLaLangue", "/confirmation",
"/categorieBS", "/ajouterPanierBS", "/panierBS", "/majPanierBS", "/commandeBS", "/acheterBS", "/selectionnerLaLangueBS", "/confirmationBS"})




public class ControllerServlet extends HttpServlet {
    
    @EJB
    private CategorieFacade categorieFacade;
    
    @EJB
    private OrderManager orderManager;
    
    
    Categorie categorieSelectionne;
    Object produitsParCategorie;
    private ProduitFacade produitFacade;
    
     private String surcharge;
    

    @Override
     public void init(ServletConfig servletConfig) throws ServletException {

         super.init(servletConfig);
        // store category list in servlet context
        getServletContext().setAttribute("categories", categorieFacade.findAll());
         surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

         String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Categorie categorieSelectionne;
        Collection<Produit> categoryProducts;

        // if category page is requested
        if (userPath.equals("/categorie")) {
            // TODO: Implement category request
            
            // get categoryId from request
            String idCategorie = request.getQueryString();

            if (idCategorie != null) {
                // get selected category
                categorieSelectionne = categorieFacade.find(Short.parseShort(idCategorie));
                // place selected category in request scope
                request.setAttribute("categorieSelectionne", categorieSelectionne);
                // get all products for selected category
                produitsParCategorie = categorieSelectionne.getProduitCollection();
                // place category products in request scope
                request.setAttribute("produitsParCategorie", produitsParCategorie);
            }

        // if cart page is requested
        } else if (userPath.equals("/ajouterPanier")) {
            // TODO: Implement cart page request
            
            String clear = request.getParameter("clear");

             if ((clear != null) && clear.equals("true")) {

                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                cart.clear();
            }

            userPath = "/panier";

        // if checkout page is requested
        } else if (userPath.equals("/passerLaCommande")) {
            // TODO: Implement checkout page request
            
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

            // calculate total
            cart.calculateTotal(surcharge);

        // if user switches language
        } else if (userPath.equals("/selectionnerLaLangue")) {
            // TODO: Implement language request
            
               String language = request.getParameter("language");

            // place in request scope
            request.setAttribute("language", language);

            // forward request to welcome page
            try {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;

        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        
        Validator validator = new Validator();
        
        if (userPath.equals("/ajouterPanier")) {
            // TODO: Implement add product to cart action
            
            // if user is adding item to cart for first time
            // create cart object and attach it to user session
            if (cart == null) {

                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }

            // get user input from request
            String productId = request.getParameter("produitID");

            if (!productId.isEmpty()) {

                Produit product;
                product = produitFacade.find(Integer.parseInt(productId));
                cart.addItem(product);
            }
            
             userPath = "/panier";

        
        } else if (userPath.equals("/majPanier")) {
            // TODO: Implement update cart action
            
             // get input from request
            String productId = request.getParameter("id");
            String quantity = request.getParameter("quantite");

           
          

            boolean invalidEntry = validator.validateQuantity(productId, quantity);

                if (!invalidEntry) {

                Produit product = produitFacade.find(Integer.parseInt(productId));
                cart.update(product, quantity);
            }

            userPath = "/panier";

        
        } else if (userPath.equals("/acheter")) {
            // TODO: Implement purchase action
 if (cart != null) {

                // extract user data from request
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String cityRegion = request.getParameter("cityRegion");
                String ccNumber = request.getParameter("creditcard");

                // validate user data
                boolean validationErrorFlag = false;
                validationErrorFlag = validator.validateForm(name, email, phone, address, cityRegion, ccNumber, request);

                // if validation error found, return user to checkout
                if (validationErrorFlag == true) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/checkout";

                    // otherwise, save order to database
                } else {

                    int orderId = orderManager.placeOrder(name, email, phone, address, cityRegion, ccNumber, cart);

                    // if order processed successfully send user to confirmation page
                    if (orderId != 0) {

                        // dissociate shopping cart from session
                        cart = null;

                        // end session
                        session.invalidate();

                        // get order details
                        Map orderMap = orderManager.getOrderDetails(orderId);

                        // place order details in request scope
                        request.setAttribute("customer", orderMap.get("customer"));
                        request.setAttribute("products", orderMap.get("products"));
                        request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                        request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));

                        userPath = "/confirmation";

                    // otherwise, send back to checkout page and display error
                    } else {
                        userPath = "/checkout";
                        request.setAttribute("orderFailureFlag", true);
                    }
                }
            }
            userPath = "/confirmation";
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

