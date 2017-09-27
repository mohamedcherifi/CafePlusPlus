/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Client;
import entity.Commande;
import entity.CommandeHasProduit;
import entity.CommandeHasProduitPK;
import entity.Produit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {

    @PersistenceContext(unitName = "CafePlusPlusPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    @EJB
    private ProduitFacade productFacade;
    @EJB
    private CommandeFacade customerOrderFacade;
    @EJB
    private CommandeHasProduitFacade orderedProductFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {

        try {
            Client client = ajouterClient(name, email, phone, address, cityRegion, ccNumber);
            Commande order = addOrder(client, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    private Client ajouterClient(String name, String email, String phone, String address, String cityRegion, String ccNumber) {

        Client client = new Client();
        client.setNom(name);
        client.setCourriel(email);
        client.setTelephone(phone);
        client.setAdresse(address);
        client.setVilleRegion(cityRegion);
        client.setCarteDeCredit(ccNumber);

        em.persist(client);
        return client;
    }

    private Commande addOrder(Client client, ShoppingCart cart) {

        // set up client order
        Commande order = new Commande();
        order.setClientId(client);
        order.setMontant(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setNumeroConfirmation(i);

        em.persist(order);
        return order;
    }

    private void addOrderedItems(Commande order, ShoppingCart cart) {

        em.flush();

        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {

            int productId = scItem.getProduct().getId();

            // set up primary key object
            CommandeHasProduitPK orderedProductPK = new CommandeHasProduitPK();
            orderedProductPK.setCommandeId(order.getId());
            orderedProductPK.setProduitId(productId);

            // create ordered item using PK object
            CommandeHasProduit orderedItem = new CommandeHasProduit(orderedProductPK);

            // set quantity
            orderedItem.setNb((int)scItem.getQuantity());

            em.persist(orderedItem);
        }
    }

    public Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();

        // get order
        Commande order = customerOrderFacade.find(orderId);

        // get client
        Client client = order.getClientId();

        // get all ordered products
        List<CommandeHasProduit> orderedProducts = orderedProductFacade.findByOrderId(orderId);

        // get product details for ordered items
        List<Produit> products = new ArrayList<Produit>();

        for (CommandeHasProduit op : orderedProducts) {

            Produit p = (Produit) productFacade.find(op.getCommandeHasProduitPK().getProduitId());
            products.add(p);
        }

        // add each item to orderMap
        orderMap.put("orderRecord", order);
        orderMap.put("client", client);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("produits", products);

        return orderMap;
    }

}