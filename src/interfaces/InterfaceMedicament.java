/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import Entity.Medicament;
import java.util.List;

/**
 *
 * @author bytesudoer
 */
public interface InterfaceMedicament {

    public void ajouterMedicament(Medicament m);
    public void modifierMedicament(Medicament m);
    public void supprimerMedicament(int id);
    public List<Medicament> afficherMedicament();
    public String convertiCritere(String str);
    public String convertirOrdre(String ordre);
    public List<Medicament> trierMedicament(String critere,String ordre);
    public Medicament trouverParId(int id);
	public Medicament trouverParNom(String nom);
}
