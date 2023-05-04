/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.List;
import Entity.Ordonnance;
import Entity.OrdonnanceN;

/**
 *
 * @author bytesudoer
 */
public interface InterfaceOrdonnance {

    // Fonctions Crud
    public void ajouterOrdonnance(OrdonnanceN o);
    public void modifierOrdonnance(OrdonnanceN o);
    public void supprimerOrdonnance(int id);
    public List<OrdonnanceN> afficherOrdonnance();
    public String converitCritere(String str);
    public String convertirOrdre(String str);
    public List<OrdonnanceN> trierOrdonnances(String critere,String ordre);
    
}
