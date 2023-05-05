/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Admin;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class AdminMainController implements Initializable {

    @FXML
    private Button ajouterID;
    @FXML
    private Button modifierID;
    @FXML
    private Button listeID;
    @FXML
    private Button testID;
    @FXML
    private Button statID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ajouterID.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Admin/AjouterArticle.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        modifierID.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Admin/modifier.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        listeID.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Admin/ListeArticle.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        statID.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Admin/PiChartView.fxml"));
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
