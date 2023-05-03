/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.categorie;

import java.util.List;


/**
 *
 * @author rouja
 */
public interface InterfaceCategorie {
     public void ajouterCategorie(categorie p );
      public void modifierCategorie(int id,String nom, String description );
       public void supprimerCategorie(int id );
          public List<categorie> afficherCategorie();
        
    
}
