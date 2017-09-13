/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author A.Sashcov
 */
@Entity
@Table(name = "commande_has_produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommandeHasProduit.findAll", query = "SELECT c FROM CommandeHasProduit c")
    , @NamedQuery(name = "CommandeHasProduit.findByCommandeId", query = "SELECT c FROM CommandeHasProduit c WHERE c.commandeHasProduitPK.commandeId = :commandeId")
    , @NamedQuery(name = "CommandeHasProduit.findByProduitId", query = "SELECT c FROM CommandeHasProduit c WHERE c.commandeHasProduitPK.produitId = :produitId")
    , @NamedQuery(name = "CommandeHasProduit.findByNb", query = "SELECT c FROM CommandeHasProduit c WHERE c.nb = :nb")})
public class CommandeHasProduit implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommandeHasProduitPK commandeHasProduitPK;
    @Column(name = "nb")
    private Integer nb;
    @JoinColumn(name = "commande_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Commande commande;
    @JoinColumn(name = "produit_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produit produit;

    public CommandeHasProduit() {
    }

    public CommandeHasProduit(CommandeHasProduitPK commandeHasProduitPK) {
        this.commandeHasProduitPK = commandeHasProduitPK;
    }

    public CommandeHasProduit(int commandeId, int produitId) {
        this.commandeHasProduitPK = new CommandeHasProduitPK(commandeId, produitId);
    }

    public CommandeHasProduitPK getCommandeHasProduitPK() {
        return commandeHasProduitPK;
    }

    public void setCommandeHasProduitPK(CommandeHasProduitPK commandeHasProduitPK) {
        this.commandeHasProduitPK = commandeHasProduitPK;
    }

    public Integer getNb() {
        return nb;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commandeHasProduitPK != null ? commandeHasProduitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommandeHasProduit)) {
            return false;
        }
        CommandeHasProduit other = (CommandeHasProduit) object;
        if ((this.commandeHasProduitPK == null && other.commandeHasProduitPK != null) || (this.commandeHasProduitPK != null && !this.commandeHasProduitPK.equals(other.commandeHasProduitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CommandeHasProduit[ commandeHasProduitPK=" + commandeHasProduitPK + " ]";
    }
    
}
