/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author L390
 */
public class RendezVous {
     private int id ; 
    private Timestamp date ; 
    private Utilisateur medecin ; 
    private Utilisateur patient ; 
    private String description ; 
    private String etat ; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Utilisateur getMedecin() {
        return medecin;
    }

    public void setMedecin(Utilisateur medecin) {
        this.medecin = medecin;
    }

    public Utilisateur getPatient() {
        return patient;
    }

    public void setPatient(Utilisateur patient) {
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
      @Override
    public String toString() {
        return "RendezVous{" + "date=" + date + ", medecin=" + medecin + ", patient=" + patient + ", description=" + description + ", etat=" + etat + '}';
    }

    public RendezVous(Timestamp date, Utilisateur medecin, Utilisateur patient, String description, String etat) {
        this.date = date;
        this.medecin = medecin;
        this.patient = patient;
        this.description = description;
        this.etat = etat;
    }

    public RendezVous(Timestamp date, String description, String etat) {
        this.date = date;
        this.description = description;
        this.etat = etat;
    
    }

}
