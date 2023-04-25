/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.categorie;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CategorieCrud;

/**
 * FXML Controller class
 *
 * @author rouja
 */
public class OlaController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button home;
    @FXML
    private Button btnvalider;

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

    @FXML
    private void saveCategorie(MouseEvent event) {
        String nom = tfNom.getText();
        String description = tfDescription.getText(); 
        categorie p =new categorie(nom,description);
        CategorieCrud c = new CategorieCrud();
        
        c.ajouterCategorie(p);
    }


    
}
