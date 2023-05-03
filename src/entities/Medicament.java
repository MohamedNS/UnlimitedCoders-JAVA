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
public class Medicament {
     private int id;
    private String nom;
    private int dosage;
    private float prix;
    private String description;

    public Medicament() {
    }

    public Medicament(int id, String nom, int dosage, float prix, String description) {
        this.id = id;
        this.nom = nom;
        this.dosage = dosage;
        this.prix = prix;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Medicament{" + "id=" + id + ", nom=" + nom + ", dosage=" + dosage + ", prix=" + prix + ", description=" + description + '}';
    }
}
