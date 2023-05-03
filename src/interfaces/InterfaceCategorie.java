/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package interfaces;

import Entity.categorie;

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
