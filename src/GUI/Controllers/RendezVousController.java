/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import Entity.Calendrier;
import ressources.SoundPlayer;
import Entity.RendezVous;
import Entity.Utilisateur;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.E;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import Services.RendezVousCrud;
import Services.UtilisateurCrud;
import ressources.SoundPlayer;
import Services.CalendrierCrud;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class RendezVousController implements Initializable {

    @FXML
    private ComboBox<Utilisateur> medecinID;
    @FXML
    private ComboBox<String> etatID;
    @FXML
    private TextField DescriptionID;
    @FXML
    private DatePicker DateID;
    @FXML
    private ComboBox<LocalTime> timeID;
    @FXML
    private ComboBox<Timestamp> suggestID;
    @FXML
    private Label medecinLabel;
    @FXML
    private Label etatLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Button btnID;
    @FXML
    private Button backID;
    @FXML
    private Label timeLabel;
    @FXML
    private Button calendarButton;
    @FXML
    private AnchorPane container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
            ObservableList<Utilisateur> medecins = utilisateurCrud.getAllMedecins();
            medecinID.getItems().addAll(medecins);
            medecinID.setConverter(new StringConverter<Utilisateur>() {
                @Override
                public String toString(Utilisateur medecin) {
                    return medecin.getNom() + " " + medecin.getPrenom();
                }

                @Override
                public Utilisateur fromString(String string) {
                    // not used, so we can return null
                    return null;
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(ex.getMessage());
        }

        // Add a listener to the medecinID combobox selection to refresh the data when a new medecin is selected
        medecinID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedMedecin) -> {
            if (selectedMedecin != null) {
                try {
                    refreshData(selectedMedecin.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(RendezVousController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

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

    private void refreshData(int doctorId) throws SQLException {
        RendezVousCrud rdc = new RendezVousCrud();
        List<Timestamp> availableTimes = rdc.getAvailableTimesForDoctor(doctorId);
        suggestID.setItems(FXCollections.observableArrayList(availableTimes));
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        Utilisateur medecin = (Utilisateur) medecinID.getValue();
        String etat = etatID.getValue();
        String description = DescriptionID.getText();
        LocalDate localDate = DateID.getValue();
        LocalTime localTime = (LocalTime) timeID.getValue();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        Instant instant = Instant.from(localDateTime.atZone(ZoneId.systemDefault()));
        Timestamp date = Timestamp.from(instant);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime selectedDateTime = date.toLocalDateTime();
        UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
        Utilisateur patient = utilisateurCrud.getPatientById(8);
        RendezVousCrud rdc = new RendezVousCrud();

        if (selectedDateTime.isBefore(currentDateTime)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selected date is in the past!");
            AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/error.mp3").toString());
            alertSound.play();
            alert.showAndWait();

        } else if (etat.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Etat and description fields cannot be empty!");
            AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/error.mp3").toString());
            alertSound.play();
            alert.showAndWait();

        } else if (rdc.isRdvAlreadyReserved(medecin.getId(), date)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("This date and time are already reserved for the selected doctor!");
            AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/error.mp3").toString());
            alertSound.play();
            alert.showAndWait();

        } else {
            RendezVous rdv = new RendezVous(date, medecin, patient, description, etat);
            rdc.ajouterRdv2(rdv);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Rendez-vous ajouté avec succès!");
            AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/notfication.mp3").toString());
            alertSound.play();
            alert.showAndWait();
        }
    }

    @FXML
    private void toCalendar(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ReserverRDV.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/MainPage.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        //Get the current window and set the scene to the main page scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }
}
