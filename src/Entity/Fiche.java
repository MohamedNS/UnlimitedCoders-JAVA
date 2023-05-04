/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.time.LocalDate;

/**
 *
 * @author asus
 */
public class Fiche {
    private int idFiche, idPatient;
    private LocalDate dateFiche;
    private float montantConsultation, montantMedicaments;

    public Fiche() {
    }

    public Fiche(int idFiche) {
        this.idFiche = idFiche;
    }
    
    

    public Fiche(int idFiche, int idPatient, LocalDate dateFiche, float montantConsultation, float montantMedicaments) {
        this.idFiche = idFiche;
        this.idPatient = idPatient;
        this.dateFiche = dateFiche;
        this.montantConsultation = montantConsultation;
        this.montantMedicaments = montantMedicaments;
    }

    public Fiche(int idPatient, LocalDate dateFiche, float montantConsultation, float montantMedicaments) {
        this.idPatient = idPatient;
        this.dateFiche = dateFiche;
        this.montantConsultation = montantConsultation;
        this.montantMedicaments = montantMedicaments;
    }

    public Fiche(LocalDate dateFiche, float montantConsultation, float montantMedicaments) {
        this.dateFiche = dateFiche;
        this.montantConsultation = montantConsultation;
        this.montantMedicaments = montantMedicaments;
    }

    public int getIdFiche() {
        return idFiche;
    }

    public void setIdFiche(int idFiche) {
        this.idFiche = idFiche;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public LocalDate getDateFiche() {
        return dateFiche;
    }

    public void setDateFiche(LocalDate dateFiche) {
        this.dateFiche = dateFiche;
    }

    public float getMontantConsultation() {
        return montantConsultation;
    }

    public void setMontantConsultation(float montantConsultation) {
        this.montantConsultation = montantConsultation;
    }

    public float getMontantMedicaments() {
        return montantMedicaments;
    }

    public void setMontantMedicaments(float montantMedicaments) {
        this.montantMedicaments = montantMedicaments;
    }

    @Override
    public String toString() {
        return "Fiche{" + "idFiche=" + idFiche + ", idPatient=" + idPatient + ", dateFiche=" + dateFiche + ", montantConsultation=" + montantConsultation + ", montantMedicaments=" + montantMedicaments + '}';
    }
    
}
