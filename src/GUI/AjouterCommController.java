/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.categorie;
import entities.commande;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CategorieCrud;
import services.CommandeCrud;

/**
 * FXML Controller class
 *
 * @author rouja
 */
public class AjouterCommController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private TextField prix;
    @FXML
    private TextField quantite;
    @FXML
    private Button ajoutercomm;

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
    private void ajoutercomm(MouseEvent event) {
         
             
         
        String prixText= prix.getText().trim();
        String quantiteText= quantite.getText().trim();
        commande p =null;
        CommandeCrud c = new CommandeCrud();
        if (prixText.isEmpty() || quantiteText.isEmpty()) {
    Alert alertError = new Alert(AlertType.ERROR);
    alertError.setTitle("Erreur");
    alertError.setHeaderText("Échec de l'ajout de la commande");
    alertError.setContentText("Veuillez remplir tous les champs.");
    alertError.showAndWait();
    return;
}

try {
    double Prix = Double.parseDouble(prixText);
    int Quantite = Integer.parseInt(quantiteText);
    if (Prix < 0 || Quantite < 0) {
        Alert alertError = new Alert(AlertType.ERROR);
        alertError.setTitle("Erreur");
        alertError.setHeaderText("Échec de l'ajout de la commande");
        alertError.setContentText("Les valeurs du Prix et de la Quantité ne peuvent pas être négatives.");
        alertError.showAndWait();
        return;
    }
    p = new commande(Prix, Quantite);
    c.ajouterCommande(p);
    Alert alertSuccess = new Alert(AlertType.INFORMATION);
    alertSuccess.setTitle("Succès");
    alertSuccess.setHeaderText("Ajout de la commande réussi");
    alertSuccess.setContentText("La commande a été ajoutée à la base de données.");
    alertSuccess.showAndWait();
} catch (NumberFormatException e) {
    Alert alertError = new Alert(AlertType.ERROR);
    alertError.setTitle("Erreur");
    alertError.setHeaderText("Échec de l'ajout de la commande");
    alertError.setContentText("Veuillez saisir des valeurs numériques valides pour le Prix et la Quantité.");
    alertError.showAndWait();
}

        c.ajouterCommande(p);
        System.out.println(p);
    }
    
}
