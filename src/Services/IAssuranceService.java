/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author asus
 */
public interface IAssuranceService<A> {
    
    public Boolean ajouter(A a);

    public Boolean supprimer(A a);

    public Boolean modifier(A a);
    
    public ObservableList<A> afficher();
    
    public List<A> listerAssurance();
}
