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
public class Remboursement {

    private int idRemboursement, idDepot;
    private String reponse;
    private LocalDate dateRemboursement;
    private float montantRembourse;

    public Remboursement() {
    }
    
    

    public Remboursement(int idRemboursement) {
        this.idRemboursement = idRemboursement;
    }

    public Remboursement(int idRemboursement, int idDepot, String reponse, LocalDate dateRemboursement, float montantRembourse) {
        this.idRemboursement = idRemboursement;
        this.idDepot = idDepot;
        this.reponse = reponse;
        this.dateRemboursement = dateRemboursement;
        this.montantRembourse = montantRembourse;
    }

    public Remboursement(String reponse, LocalDate dateRemboursement, float montantRembourse) {
        this.reponse = reponse;
        this.dateRemboursement = dateRemboursement;
        this.montantRembourse = montantRembourse;
    }
    
    public Remboursement(int idDepot, String reponse, LocalDate dateRemboursement, float montantRembourse) {
        this.idDepot = idDepot;
        this.reponse = reponse;
        this.dateRemboursement = dateRemboursement;
        this.montantRembourse = montantRembourse;
    }

    public Remboursement(float montantRembourse) {
        this.montantRembourse = montantRembourse;
    }

    public int getIdRemboursement() {
        return idRemboursement;
    }

    public void setIdRemboursement(int idRemboursement) {
        this.idRemboursement = idRemboursement;
    }

    public int getIdDepot() {
        return idDepot;
    }

    public void setIdDepot(int idDepot) {
        this.idDepot = idDepot;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public LocalDate getDateRemboursement() {
        return dateRemboursement;
    }

    public void setDateRemboursement(LocalDate dateRemboursement) {
        this.dateRemboursement = dateRemboursement;
    }

    public float getMontantRembourse() {
        return montantRembourse;
    }

    public void setMontantRembourse(float montantRembourse) {
        this.montantRembourse = montantRembourse;
    }

    @Override
    public String toString() {
        return "Remboursement{" + "idRemboursement=" + idRemboursement + ", idDepot=" + idDepot + ", reponse=" + reponse + ", dateRemboursement=" + dateRemboursement + ", montantRembourse=" + montantRembourse + '}';
    }

}
