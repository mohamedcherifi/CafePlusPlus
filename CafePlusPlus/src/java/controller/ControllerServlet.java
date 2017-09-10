/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author A.Sashcov
 */
@WebServlet(name = "ControllerServlet",  loadOnStartup = 1, urlPatterns = {"/categorie", "/ajouterPanier", "/visualiserPanier", "/majPanier", "/passerLaCommande", "/acheter", "/selectionnerLaLangue", "/confirmation",
"/categorieBS", "/ajouterPanierBS", "/panierBS", "/majPanierBS", "/commandeBS", "/acheterBS", "/selectionnerLaLangueBS", "/confirmationBS"})




public class ControllerServlet extends HttpServlet {

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
        if (userPath.equals("/categorieBS")) {
            // TODO: Implement category request

        // if cart page is requested
        } else if (userPath.equals("/ajouterPanierBS")) {
            // TODO: Implement cart page request

            userPath = "/panierBS";

        // if checkout page is requested
        } else if (userPath.equals("/passerLaCommandeBS")) {
            // TODO: Implement checkout page request

        // if user switches language
        } else if (userPath.equals("/selectionnerLaLangueBS")) {
            // TODO: Implement language request

        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/viewBS" + userPath + ".jsp";

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

        // if addToCart action is called
        if (userPath.equals("/ajouterPanierBS")) {
            // TODO: Implement add product to cart action

        // if updateCart action is called
        } else if (userPath.equals("/majPanierBS")) {
            // TODO: Implement update cart action

        // if purchase action is called
        } else if (userPath.equals("/acheterBS")) {
            // TODO: Implement purchase action

            userPath = "/confirmationBS";
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/viewBS" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

