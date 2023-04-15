/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.List;
import Entity.Ordonnance;

/**
 *
 * @author bytesudoer
 */
public interface InterfaceOrdonnance {

    // Fonctions Crud
    public void ajouterOrdonnance(Ordonnance o);
    public void modifierOrdonnance(Ordonnance o);
    public void supprimerOrdonnance(int id);
    public List<Ordonnance> afficherOrdonnance();
    public String converitCritere(String str);
    public String convertirOrdre(String str);
    public List<Ordonnance> trierOrdonnances(String critere,String ordre);
    
}
