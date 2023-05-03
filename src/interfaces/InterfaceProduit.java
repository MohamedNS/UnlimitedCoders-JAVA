
package interfaces;

import entities.produit;
import java.util.List;
import javafx.collections.ObservableList;



/**
 *
 * @author roujas
 */
public interface InterfaceProduit {
    public void ajouterProduit(produit p );
     public void ajouterProduit2(produit p );
      public void modifierProduit(produit prod );
       public void supprimerProduit(produit p );
        public List<produit> afficherProduit();
}
