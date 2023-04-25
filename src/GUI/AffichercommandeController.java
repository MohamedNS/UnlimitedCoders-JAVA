/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.commande;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CommandeCrud;

/**
 * FXML Controller class
 *
 * @author rouja
 */
public class AffichercommandeController implements Initializable {

    @FXML
    private TableView<commande> tabCommande;
    @FXML
    private TableColumn<commande, String> colprix;
    @FXML
    private TableColumn<commande, String> colquantite;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    @FXML
    private void home(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
    
    private ObservableList<commande> Commandes;
    private void affichercommande(MouseEvent event) {
         CommandeCrud p = new CommandeCrud();
         Commandes= p.afficherCommande() ;
        
        
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tabCommande.setItems(Commandes);
      
        
  }

    
}
