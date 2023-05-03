/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import Entity.RendezVous;
import Entity.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import Services.RendezVousCrud;
import Services.UtilisateurCrud;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class ModifierRdvController implements Initializable {

    @FXML
    private Button modifierID;
    @FXML
    private ComboBox<String> etatID;
    @FXML
    private TextField descriptionID;
    @FXML
    private DatePicker dateID;
    @FXML
    private ComboBox<RendezVous> rdvID;
    @FXML
    private Button BackTomainID;
    @FXML
    private ComboBox<LocalTime> timeID;
    @FXML
    private Label rdvLabel;
    @FXML
    private Label etatLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private AnchorPane container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
            Utilisateur patient = utilisateurCrud.getPatientById(8);
            RendezVousCrud rendezVousCrud = new RendezVousCrud();
            List<RendezVous> rendezVousList = rendezVousCrud.getRendezVousByUtilisateurId(patient.getId());
            ObservableList<RendezVous> observableList = FXCollections.observableList(rendezVousList);
            rdvID.setItems(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage());
        }
        timeID.getItems().addAll(
                LocalTime.of(8, 0),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0)
        );
         etatID.getItems()
                .addAll(
                        "URGENT",
                        "NON URGENT",
                        "SUIVI",
                        "CONSULTAION DE ROUTINE"
                );

    }

    @FXML
    private void ModifierRdvSave(ActionEvent event) throws SQLException {
        RendezVous r = (RendezVous) rdvID.getValue();
        int rdvid = r.getId();
        String etat = etatID.getValue();
        String description = descriptionID.getText();
        LocalDate localDate = dateID.getValue();
        LocalTime localTime = timeID.getValue();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        Instant instant = Instant.from(localDateTime.atZone(ZoneId.systemDefault()));
        Timestamp date = Timestamp.from(instant);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime selectedDateTime = date.toLocalDateTime();
        UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
        Utilisateur patient = utilisateurCrud.getPatientById(8);
        RendezVousCrud rdc = new RendezVousCrud();
        Utilisateur medecin = r.getMedecin();

        if (etat.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Etat and description fields cannot be empty!");
             AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/error.mp3").toString());
            alertSound.play();
            alert.showAndWait();

        } else if (selectedDateTime.isBefore(currentDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selected date is in the past!");
             AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/error.mp3").toString());
            alertSound.play();
            alert.showAndWait();
        } else if (rdc.isRdvAlreadyReserved(medecin.getId(), date)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("This date and time are already reserved for the selected doctor!");
            AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/notfication.mp3").toString());
            alertSound.play();
            alert.showAndWait();
        } else {

            rdc.modifierRdv2(rdvid, date, patient, etat, description);
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Rendez-vous modifié avec succès!");
            alert.showAndWait();
        }

    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/MainPage.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }

}
