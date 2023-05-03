/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import Entity.Calendrier;
import Entity.RendezVous;
import Entity.Utilisateur;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import Services.CalendrierCrud;
import Services.RendezVousCrud;
import Services.UtilisateurCrud;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class ConsulterDispoController implements Initializable {

    @FXML
    private TableColumn<Calendrier, LocalDateTime> endID;
    @FXML
    private Button backID;
    @FXML
    private TableColumn<Calendrier, Integer> dispoID;
    @FXML
    private TableColumn<Calendrier, LocalDateTime> startdadteID;
    @FXML
    private TableView<Calendrier> tableID;
    @FXML
    private TableColumn<Calendrier, Void> deleteID;
    @FXML
    private Button toCalendar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {

            dispoID.setCellValueFactory(new PropertyValueFactory<>("id"));
            startdadteID.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
            endID.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
            // Populate table with data
            UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
            Utilisateur medecin = utilisateurCrud.getMedecinById(6);
            CalendrierCrud calendrierCrud = new CalendrierCrud();
            List<Calendrier> calendrierList = calendrierCrud.getDispoByMedecinId(medecin.getId());
            ObservableList<Calendrier> observableList = FXCollections.observableList(calendrierList);
            tableID.setItems(observableList);

            // Set up column with delete button for each row
            Callback<TableColumn<Calendrier, Void>, TableCell<Calendrier, Void>> cellFactory = new Callback<TableColumn<Calendrier, Void>, TableCell<Calendrier, Void>>() {
                @Override
                public TableCell<Calendrier, Void> call(final TableColumn<Calendrier, Void> param) {
                    final TableCell<Calendrier, Void> cell = new TableCell<Calendrier, Void>() {

                        private final Button deleteButton = new Button("Supprimer");

                        {
                            deleteButton.setOnAction((ActionEvent event) -> {
                                Calendrier calendrier = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Supprimer une disponibilité");
                                alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer cette disponibilité ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    CalendrierCrud calendrierCrud = new CalendrierCrud();
                                    calendrierCrud.supprimerDispo(calendrier.getId());
                                    tableID.getItems().remove(calendrier);
                                }
                            });

                        }

                        //// updating the graphical representation of the cell.
                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(deleteButton);
                            }
                        }
                    };
                    return cell;
                }
            };
            deleteID.setCellFactory(cellFactory);
            tableID.setItems(observableList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Medecin.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }

    @FXML
    private void toCalendar(ActionEvent event) {
        try {
            Utilisateur medecin = new Utilisateur(6);
            displayDoctorCalendar(medecin);
        } catch (SQLException ex) {
            Logger.getLogger(ConsulterDispoController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        Stage stage = new Stage();
        Scene scene = new Scene(calendarView);
        stage.setScene(scene);
        stage.show();

        // Create a new stage and set its scene to the calendar view
    }
}
