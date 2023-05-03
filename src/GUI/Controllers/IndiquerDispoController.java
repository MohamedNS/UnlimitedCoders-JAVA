/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import Entity.Calendrier;
import Entity.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import Services.CalendrierCrud;
import Services.UtilisateurCrud;
import static Services.UtilisateurCrud.cnx2;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class IndiquerDispoController implements Initializable {

    @FXML
    private DatePicker dateID;
    @FXML
    private ComboBox<LocalTime> startID;
    @FXML
    private ComboBox<LocalTime> endID;
    @FXML
    private Button backID;
    @FXML
    private Button indiquerID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startID.getItems().addAll(
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
        endID.getItems().addAll(
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

    }

    @FXML
    private void saveDispo(ActionEvent event) throws SQLException {
        LocalDate localDate = dateID.getValue();
        LocalTime startTime = startID.getValue();
        LocalTime endTime = endID.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(localDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(localDate, endTime);
        if (localDate.isBefore(LocalDate.now())) {
            // Show an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("The date cannot be in the past");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("mac-theme");
            alert.showAndWait();

            return;
        }
        // Check if the end time is greater than the start time
        if (endDateTime.isBefore(startDateTime) || endDateTime.equals(startDateTime)) {
            // Show an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("The end time must be greater than the start time");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("mac-theme");
            alert.showAndWait();
            return;
        }
        UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
        Utilisateur medecin = utilisateurCrud.getMedecinById(6);
        CalendrierCrud cc = new CalendrierCrud();
        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
        Calendrier cal = new Calendrier(medecin, startTimestamp, endTimestamp);
        cc.ajouterDispo(cal);
    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Medecin.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }

}
