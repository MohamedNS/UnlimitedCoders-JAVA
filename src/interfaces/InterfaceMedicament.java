/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import Entity.Medicament;
import Entity.MedicamentN;
import java.util.List;

/**
 *
 * @author bytesudoer
 */
public interface InterfaceMedicament {

    public void ajouterMedicament(MedicamentN m);
    public void modifierMedicament(MedicamentN m);
    public void supprimerMedicament(int id);
    public List<MedicamentN> afficherMedicament();
    public String convertiCritere(String str);
    public String convertirOrdre(String ordre);
    public List<MedicamentN> trierMedicament(String critere,String ordre);
    public MedicamentN trouverParId(int id);
	public MedicamentN trouverParNom(String nom);
	public float calculerMoyennePrix();
}
