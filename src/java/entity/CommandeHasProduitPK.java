/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author A.Sashcov
 */
@Embeddable
public class CommandeHasProduitPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "commande_id")
    private int commandeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "produit_id")
    private int produitId;

    public CommandeHasProduitPK() {
    }

    public CommandeHasProduitPK(int commandeId, int produitId) {
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) commandeId;
        hash += (int) produitId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommandeHasProduitPK)) {
            return false;
        }
        CommandeHasProduitPK other = (CommandeHasProduitPK) object;
        if (this.commandeId != other.commandeId) {
            return false;
        }
        if (this.produitId != other.produitId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CommandeHasProduitPK[ commandeId=" + commandeId + ", produitId=" + produitId + " ]";
    }
    
}
