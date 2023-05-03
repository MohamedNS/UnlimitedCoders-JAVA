/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class MainPageMedecinController implements Initializable {

    @FXML
    private Button indiquerID;
    @FXML
    private Button consulterID;
    private Button modifierDispoID;
    @FXML
    private Button statID;
    @FXML
    private AnchorPane window;
    @FXML
    private AnchorPane container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         indiquerID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/IndiquerDispo.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
});
         consulterID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ConsulterDispo.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
});
         modifierDispoID.setOnAction((event) -> {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Statistique.fxml"));
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
    
}
