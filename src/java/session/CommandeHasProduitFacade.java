/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CommandeHasProduit;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entity.Produit;

/**
 *
 * @author A.Sashcov
 */
@Stateless
public class CommandeHasProduitFacade extends AbstractFacade<CommandeHasProduit> {

    @PersistenceContext(unitName = "CafePlusPlusPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeHasProduitFacade() {
        super(CommandeHasProduit.class);
    }
    
   
    /*TODO: A VOIR*/
    // manually created
    public List<CommandeHasProduit> findByOrderId(Object id) {
        return em.createNamedQuery("Produit.findByCustomerOrderId").setParameter("customerOrderId", id).getResultList();
    }
    
}
