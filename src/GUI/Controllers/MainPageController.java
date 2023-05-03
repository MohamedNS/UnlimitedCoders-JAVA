/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class MainPageController implements Initializable {

    @FXML
    private Button ReserverID;
    @FXML
    private Button AnnulerID;
    @FXML
    private Button AfficherID;
    @FXML
    private Button ModifierID;
    @FXML
    private AnchorPane container;
    @FXML
    private Button backID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReserverID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/RendezVous.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
});
         ModifierID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ModifierRdv.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
});
          AnnulerID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AnnulerRdv.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
});
           AfficherID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ConsulterRdv.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
});
    }    
    @FXML
    private void BackToMainPage(ActionEvent event) {
        try {
            Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/FirstWindowRDV.fxml"));
            Scene mainPageScene = new Scene(mainPageParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MedecinController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
