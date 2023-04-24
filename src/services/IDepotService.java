/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author asus
 */
public interface IDepotService<D> {
    
    public Boolean ajouter(D d);

    public Boolean supprimer(D d);

    public Boolean modifier(D d);
    
    public ObservableList<D> afficher();
    
    public void calculerTotalDepense(D d);
    public List<String> listerIDsDepots();

}
