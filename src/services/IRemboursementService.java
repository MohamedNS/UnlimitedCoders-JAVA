/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javafx.collections.ObservableList;

/**
 *
 * @author asus
 */
public interface IRemboursementService<R> {

    public Boolean ajouter(R r);

    public Boolean supprimer(R r);

    public Boolean modifier(R r);
    
    public ObservableList<R> afficher();
    
    public float calculerMontantRembourse(R r);
    
}
