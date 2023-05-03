/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author MSI
 */
public class Consultation {
      private int id;
    private String matriculeMedecin;
    private String idPatient;
    private Date dateConsultation;
    private float montant;
    
    public Consultation()
    {
    }
    public Consultation(String matriculeMedecin,String idPatient,Date dateConsultation,float montant)
    {
        this.matriculeMedecin = matriculeMedecin;
        this.idPatient = idPatient;
        this.dateConsultation = dateConsultation;
        this.montant = montant;
    }
    public Consultation(int id,String matriculeMedecin,String idPatient,Date dateConsultation,float montant)
    {
        this.id = id;
        this.matriculeMedecin = matriculeMedecin;
        this.idPatient = idPatient;
        this.dateConsultation = dateConsultation;
        this.montant = montant;
    }

    public Consultation(int id,String matriculeMedecin,String idPatient,float montant)
    {
        this.id = id;
        this.matriculeMedecin = matriculeMedecin;
        this.idPatient = idPatient;
        this.montant = montant;
    }

    //Defining Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatriculeMedecin() {
        return matriculeMedecin;
    }

    public void setMatriculeMedecin(String matriculeMedecin) {
        this.matriculeMedecin = matriculeMedecin;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public float getMontant()
    {
        return this.montant;
    }

    public void setMontant(float montant)
    {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", matriculeMedecin=" + matriculeMedecin + ", idPatient=" + idPatient + ", dateConsultation=" + dateConsultation + '}';
    }
}
