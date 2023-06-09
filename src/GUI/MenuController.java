/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class MenuController implements Initializable {

    @FXML
    private Button backID;

    public MenuController() {
    }

    @FXML
    private Button btnConsultation;
    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMedicament;

    @FXML
    private Button btnOrdonnance;

    private Button btnRetour;

    @FXML
    public void btnConsultation(ActionEvent evt) {
        try {
            System.out.println("Consultation Click");
            Parent loader = FXMLLoader.load(getClass().getResource("HomeConsultation.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnConsultation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void btnOrdonnance(ActionEvent evt) {
        try {
            System.out.println("Ordonnance Click");
            Parent loader = FXMLLoader.load(getClass().getResource("HomeOrdonnance.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnOrdonnance.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void btnMedicament(ActionEvent evt) {
        try {
            System.out.println("Medicament Click");
            Parent loader = FXMLLoader.load(getClass().getResource("HomeMedicament.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnMedicament.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void btnRetour(ActionEvent evt) {
        System.out.println("HomeConsultation fermee");
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void btnLogout(ActionEvent evt) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../GUI/BienvenuePatient.fxml"));
        try {
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = (Stage) btnConsultation.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void show() {
        try {

            System.out.println("Consultation Click");
            Parent loader = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void BackToMainPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../GUI/MenuBienvenu.fxml"));
        try {
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = (Stage) btnConsultation.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
