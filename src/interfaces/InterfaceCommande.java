/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.commande;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author rouja
 */
public interface InterfaceCommande {
     public void ajouterCommande(commande p );
      public void modifierCommande(int id,int prix,int quantite );
       public void supprimerCommande(int id );
          public ObservableList<commande> afficherCommande();
        
    
}
