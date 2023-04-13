/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author bytesudoer
 */
public class Medicament {
    private int id;
    private int code;
    private String libelle;
    private float prix;
    private String type;
    private int dosage;
    private String description;
    private String nom;

    public Medicament()
    {
    }
    public Medicament(int id,int code,String libelle,float prix,
            String type,int dosage,String description,String nom )
    {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.prix = prix;
        this.type = type;
        this.dosage = dosage;
        this.description = description;
        this.nom = nom;
    }
    public Medicament(int code,String libelle,float prix,
            String type,int dosage,String description,String nom )
    {
        this.code = code;
        this.libelle = libelle;
        this.prix = prix;
        this.type = type;
        this.dosage = dosage;
        this.description = description;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Medicament{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + ", prix=" + prix + ", type=" + type + ", dosage=" + dosage + ", description=" + description + ", nom=" + nom + '}';
    }

    
    
}
