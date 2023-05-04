/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author MSI
 */
public class FicheAssurance {
   public int id;
    public String cin;
    public String nom;
    public String prenom;
    public String addresse;
    public String matricule_cnam;
    public int matricule_fiscal;
    public int honoraires;
    public String designation;
    public Date date;

    public FicheAssurance(String cin, String nom, String prenom, String addresse, String matricule_cnam, int matricule_fiscal, int honoraires, String designation, LocalDate date, int total) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public FicheAssurance(String cin, String nom, String prenom, String addresse, String matricule_cnam, int matricule_fiscal, int honoraires, String designation, String string, int total) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public FicheAssurance(String cin, String nom, String prenom, String addresse, String matricule_cnam, int matricule_fiscal, int honoraires, int i, String string, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int total;

    public FicheAssurance(String toString, String toString0, String toString1, String toString2, int parseInt, String toString3, String toString4, int parseInt0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getMatricule_cnam() {
        return matricule_cnam;
    }

    public void setMatricule_cnam(String matricule_cnam) {
        this.matricule_cnam = matricule_cnam;
    }

    public int getMatricule_fiscal() {
        return matricule_fiscal;
    }

    public void setMatricule_fiscal(int matricule_fiscal) {
        this.matricule_fiscal = matricule_fiscal;
    }

    public int getHonoraires() {
        return honoraires;
    }

    public void setHonoraires(int honoraires) {
        this.honoraires = honoraires;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FicheAssurance{" + "id=" + id + ", cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", addresse=" + addresse + ", matricule_cnam=" + matricule_cnam + ", matricule_fiscal=" + matricule_fiscal + ", honoraires=" + honoraires + ", designation=" + designation + ", total=" + total + '}';
    }

    public FicheAssurance(String cin, String nom, String prenom, String addresse, String matricule_cnam, int matricule_fiscal, int honoraires, String designation,Date date ,int total) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.addresse = addresse;
        this.matricule_cnam = matricule_cnam;
        this.matricule_fiscal = matricule_fiscal;
        this.honoraires = honoraires;
        this.designation = designation;
        this.date = date;
        this.total = total;
    }

    public FicheAssurance() {
    }
}

