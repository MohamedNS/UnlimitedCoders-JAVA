/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author asus
 */
public class Assurance {

    private int idAssurance;
    private String nomAssurance, adresseAssurance;
    private float plafond;

    public Assurance() {
    }

    public Assurance(int idAssurance) {
        this.idAssurance = idAssurance;
    }

    public Assurance(int idAssurance, String nomAssurance) {
        this.idAssurance = idAssurance;
        this.nomAssurance = nomAssurance;
    }

    
    public Assurance(int idAssurance, String nomAssurance, String adresseAssurance, float plafond) {
        this.idAssurance = idAssurance;
        this.nomAssurance = nomAssurance;
        this.adresseAssurance = adresseAssurance;
        this.plafond = plafond;
    }

    public Assurance(String nomAssurance, String adresseAssurance, float plafond) {
        this.nomAssurance = nomAssurance;
        this.adresseAssurance = adresseAssurance;
        this.plafond = plafond;
    }

    public Assurance(String nomAssurance) {
        this.nomAssurance = nomAssurance;
    }

    public Assurance(float plafond) {
        this.plafond = plafond;
    }

    public int getIdAssurance() {
        return idAssurance;
    }

    public void setIdAssurance(int idAssurance) {
        this.idAssurance = idAssurance;
    }

    public String getNomAssurance() {
        return nomAssurance;
    }

    public void setNomAssurance(String nomAssurance) {
        this.nomAssurance = nomAssurance;
    }

    public String getAdresseAssurance() {
        return adresseAssurance;
    }

    public void setAdresseAssurance(String adresseAssurance) {
        this.adresseAssurance = adresseAssurance;
    }

    public float getPlafond() {
        return plafond;
    }

    public void setPlafond(float plafond) {
        this.plafond = plafond;
    }

    @Override
    public String toString() {
        return "Assurance{" + "idAssurance=" + idAssurance + ", nomAssurance=" + nomAssurance + ", adresseAssurance=" + adresseAssurance + ", plafond=" + plafond + '}';
    }

}
