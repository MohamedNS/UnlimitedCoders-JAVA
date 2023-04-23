/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDate;

/**
 *
 * @author asus
 */
public class Depot {

    private int idDepot, idAssurance, idFiche, idPatient;
    private LocalDate dateDepot;
    private String etat, regime;
    private float totalDepense;

    public Depot() {
    }

    public Depot(int idDepot) {
        this.idDepot = idDepot;
    }

    public Depot(int idDepot, int idAssurance, int idFiche, int idPatient, LocalDate dateDepot, String etat, String regime, float totalDepense) {
        this.idDepot = idDepot;
        this.idAssurance = idAssurance;
        this.idFiche = idFiche;
        this.idPatient = idPatient;
        this.dateDepot = dateDepot;
        this.etat = etat;
        this.regime = regime;
        this.totalDepense = totalDepense;
    }

    public Depot(int idAssurance, int idFiche, int idPatient, LocalDate dateDepot, String etat, String regime, float totalDepense) {
        this.idAssurance = idAssurance;
        this.idFiche = idFiche;
        this.idPatient = idPatient;
        this.dateDepot = dateDepot;
        this.etat = etat;
        this.regime = regime;
        this.totalDepense = totalDepense;
    }

    public Depot(LocalDate dateDepot, String etat, String regime, float totalDepense) {
        this.dateDepot = dateDepot;
        this.etat = etat;
        this.regime = regime;
        this.totalDepense = totalDepense;
    }

    public Depot(int idDepot, LocalDate dateDepot, String etat, String regime, float totalDepense) {
        this.idDepot = idDepot;
        this.dateDepot = dateDepot;
        this.etat = etat;
        this.regime = regime;
        this.totalDepense = totalDepense;
    }

    public Depot(float totalDepense) {
        this.totalDepense = totalDepense;
    }

    public int getIdDepot() {
        return idDepot;
    }

    public void setIdDepot(int idDepot) {
        this.idDepot = idDepot;
    }

    public int getIdAssurance() {
        return idAssurance;
    }

    public void setIdAssurance(int idAssurance) {
        this.idAssurance = idAssurance;
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

    public LocalDate getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDate dateDepot) {
        this.dateDepot = dateDepot;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public float getTotalDepense() {
        return totalDepense;
    }

    public void setTotalDepense(float totalDepense) {
        this.totalDepense = totalDepense;
    }

    @Override
    public String toString() {
        return "Depot{" + "idDepot=" + idDepot + ", idAssurance=" + idAssurance + ", idFiche=" + idFiche + ", idPatient=" + idPatient + ", dateDepot=" + dateDepot + ", etat=" + etat + ", regime=" + regime + ", totalDepense=" + totalDepense + '}';
    }

}
