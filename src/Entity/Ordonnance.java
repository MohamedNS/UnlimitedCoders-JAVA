/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.ArrayList;

/**
 *
 * @author bytesudoer
 */
public class Ordonnance {
    private int id;
    private int consultation_id;
    private int validite;
    private String nomMedicaments;
    private ArrayList<Medicament> medicamentOrdonnance;

    public Ordonnance()
    {
        this.medicamentOrdonnance = new ArrayList<>();
    }
    public Ordonnance(int id,int consultation_id,int validite)
    {
        this.id = id;
        this.consultation_id = consultation_id;
        this.validite = validite;
        this.medicamentOrdonnance = new ArrayList<>();
    }
    public Ordonnance(int consultation_id,int validite)
    {
        this.consultation_id = consultation_id;
        this.validite = validite;
        this.medicamentOrdonnance = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(int consultation_id) {
        this.consultation_id = consultation_id;
    }

    public int getValidite() {
        return validite;
    }

    public void setValidite(int validite) {
        this.validite = validite;
    }
    public void setNomMedicament()
    {
        this.nomMedicaments = this.createString();
    }
    public String getNomMedicaments()
    {
        return this.nomMedicaments;
    }

    public String createString()
    {
        String nomMedicaments = "";
        for(Medicament m : medicamentOrdonnance)
        {
            nomMedicaments += m.getNom()+" ";
        }
        return nomMedicaments;
    }
    public void addMedicament(Medicament m)
    {
        this.medicamentOrdonnance.add(m);
    }

    @Override
    public String toString() {
        return "Ordonnance{" + "id=" + id + ", consultation_id=" + consultation_id + ", validite=" + validite +",nomMedicament"+nomMedicaments+ ", medicament"+medicamentOrdonnance+'}';
    }
    
}
