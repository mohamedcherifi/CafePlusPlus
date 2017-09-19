/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Categorie;
import entity.Produit;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;
import session.CategorieFacade;

/**
 *
 * @author A.Sashcov
 */
@WebServlet(name = "ControllerServlet",  loadOnStartup = 1, urlPatterns = {"/categorie", "/ajouterPanier", "/visualiserPanier", "/majPanier", "/passerLaCommande", "/acheter", "/selectionnerLaLangue", "/confirmation",
"/categorieBS", "/ajouterPanierBS", "/panierBS", "/majPanierBS", "/commandeBS", "/acheterBS", "/selectionnerLaLangueBS", "/confirmationBS", "/index"})




public class ControllerServlet extends HttpServlet {
    
    @EJB
    private CategorieFacade categorieFacade;
    Categorie categorieSelectionne;
    Object produitsParCategorie;
    

    @Override
     public void init() throws ServletException {

        // store category list in servlet context
        getServletContext().setAttribute("categories", categorieFacade.findAll());
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

            userPath = "/panier";

        // if checkout page is requested
        } else if (userPath.equals("/passerLaCommande")) {
            // TODO: Implement checkout page request

        // if user switches language
        } else if (userPath.equals("/selectionnerLaLangue")) {
            // TODO: Implement language request

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

        String userPath = request.getServletPath();

        
        if (userPath.equals("/ajouterPanier")) {
            // TODO: Implement add product to cart action
            
             userPath = "/panier";

        
        } else if (userPath.equals("/majPanier")) {
            // TODO: Implement update cart action

        
        } else if (userPath.equals("/acheter")) {
            // TODO: Implement purchase action

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

