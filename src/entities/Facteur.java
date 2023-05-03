/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author MSI
 */
public class Facteur {
     public int id;
    public String cin;
    public String nom;
    public String prenom;
    public int id_patient;
    public int id_medicament;
    public String nom_med;
    public String dosage;
    public int prix; 

    public Facteur(int id, String cin, String nom, String prenom, int id_patient, int id_medicament, String nom_med, String dosage, int prix) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.id_patient = id_patient;
        this.id_medicament = id_medicament;
        this.nom_med = nom_med;
        this.dosage = dosage;
        this.prix = prix;
    }

    public Facteur() {
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

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public int getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(int id_medicament) {
        this.id_medicament = id_medicament;
    }

    public String getNom_med() {
        return nom_med;
    }

    public void setNom_med(String nom_med) {
        this.nom_med = nom_med;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return "Facteur{" + "id=" + id + ", cin=" + cin + ", nom=" + nom + ", prenom=" + prenom + ", id_patient=" + id_patient + ", id_medicament=" + id_medicament + ", nom_med=" + nom_med + ", dosage=" + dosage + ", prix=" + prix + '}';
    }

    public Facteur(String cin, String nom, String prenom, int id_patient, String nom_med, String dosage, int prix) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.id_patient = id_patient;
        this.nom_med = nom_med;
        this.dosage = dosage;
        this.prix = prix;
    }

    public Facteur(String cin, String nom, String prenom, int id_patient, int id_medicament, String nom_med, String dosage, int prix) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.id_patient = id_patient;
        this.id_medicament = id_medicament;
        this.nom_med = nom_med;
        this.dosage = dosage;
        this.prix = prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Facteur(String cin, String nom, String prenom, String nom_med, String dosage) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.nom_med = nom_med;
        this.dosage = dosage;
    }

    public Facteur(String cin, String nom, String prenom, String nom_med, String dosage, int prix) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.nom_med = nom_med;
        this.dosage = dosage;
        this.prix = prix;
    }

    public Facteur(String cin, String nom, String prenom, int id_medicament, String nom_med, String dosage) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.id_medicament = id_medicament;
        this.nom_med = nom_med;
        this.dosage = dosage;
    }
}
