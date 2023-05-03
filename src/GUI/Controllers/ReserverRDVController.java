/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import Entity.Calendrier;
import Entity.RendezVous;
import Entity.Utilisateur;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.CalendarView;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import com.calendarfx.model.CalendarSource;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Services.CalendrierCrud;
import Services.RendezVousCrud;
import Services.UtilisateurCrud;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class ReserverRDVController implements Initializable {

    @FXML
    private ComboBox<Utilisateur> medecinID;
    @FXML
    private ComboBox<String> etatID;
    @FXML
    private TextArea DescriptionID;
    @FXML
    private Button btnID;
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

        etatID.getItems()
                .addAll(
                        "URGENT",
                        "NON URGENT",
                        "SUIVI",
                        "CONSULTAION DE ROUTINE"
                );

    }

    @FXML
    private void toCalendar(ActionEvent event) throws SQLException {
        Utilisateur medecin = (Utilisateur) medecinID.getValue();
        String etat = etatID.getValue();
        String description = DescriptionID.getText();

        displayDoctorCalendar(medecin);
    }

    public void displayDoctorCalendar(Utilisateur medecin) throws SQLException {

        RendezVousCrud r = new RendezVousCrud();
        CalendrierCrud cal = new CalendrierCrud();
        List<RendezVous> appointmentsData = r.getAllRendezVousOfDoctor(medecin.getId());

        // Convert the appointments data to CalendarFX-compatible format
        List<CalendarSource> calendarData = new ArrayList<>();
        CalendarSource appointmentSource = new CalendarSource("Appointments");

        appointmentsData.stream().map(new Function<RendezVous, Entry<String>>() {
            @Override
            public Entry<String> apply(RendezVous appointment) {

                // Convert the appointment start and end times to LocalDateTime
                LocalDateTime localStart = appointment.getDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                LocalDateTime localEnd = localStart.plus(Duration.ofHours(1)); // Assume each appointment lasts 1 hour
                Interval interval = new Interval(localStart, localEnd);

                // Create a custom string for the appointment details that includes the doctor and patient information
                int doctorId = appointment.getMedecin().getId();
                int patientId = appointment.getPatient().getId();
                UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
                String doctorName = utilisateurCrud.getMedecinById(doctorId).getNom();
                String patientName = utilisateurCrud.getPatientById(patientId).getNom();
                String appointmentDetails = String.format("Appointment with Dr. %s and %s", doctorName, patientName);

                // Create a new Entry object for the appointment
                Entry<String> entryRDV = new Entry<>(appointmentDetails, interval);

                // Associate the RendezVous object with the entry
                entryRDV.setUserObject(appointmentDetails);

                return entryRDV;
            }
        }).forEach((entry) -> {
            // Add the entry to the appointment source
            appointmentSource.getCalendars().add(new Calendar("Calendar"));
            appointmentSource.getCalendars().get(appointmentSource.getCalendars().size() - 1).addEntry(entry);
        });

        List<Calendrier> availabilityData = cal.getDispoByMedecinId(medecin.getId());

        // Convert the availability data to CalendarFX-compatible format
        CalendarSource availabilitySource = new CalendarSource("Availability");
        availabilityData.stream().map((availability) -> {

            LocalDateTime localStart = availability.getHeure_debut()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime localEnd = availability.getHeure_fin()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            Interval interval = new Interval(localStart, localEnd);
            return interval;
        }).map((interval) -> {
            // Create a new Entry object for the availability
            String availabilityDetails = "Available";
            Entry<String> entry = new Entry<>(availabilityDetails, interval);

            entry.setUserObject(availabilityDetails);
            return entry;
        }).forEach((entry) -> {

            availabilitySource.getCalendars().add(new Calendar("Calendar"));
            availabilitySource.getCalendars().get(availabilitySource.getCalendars().size() - 1).addEntry(entry);
        });

        calendarData.add(availabilitySource);
        calendarData.add(appointmentSource);
        CalendarView calendarView = new CalendarView();
        calendarView.getCalendarSources().setAll(calendarData);
        calendarView.setRequestedTime(LocalTime.now());

        Scene scene = new Scene(calendarView);
        Stage stage = (Stage) btnID.getScene().getWindow();
        stage.setScene(scene);
    }
}
