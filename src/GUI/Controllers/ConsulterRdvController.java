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
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Services.CalendrierCrud;
import Services.RendezVousCrud;
import Services.UtilisateurCrud;
import com.calendarfx.model.Entry;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Callback;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class ConsulterRdvController implements Initializable {

    @FXML
    private TableColumn<RendezVous, LocalDate> dateID;
    @FXML
    private TableColumn<RendezVous, String> medecinID;
    @FXML
    private TableColumn<RendezVous, String> etatID;
    @FXML
    private TableColumn<RendezVous, String> descriptionID;
    @FXML
    private Button backID;
    @FXML
    private TableView<RendezVous> tableView;
    @FXML
    private Button calendarID;
    @FXML
    private AnchorPane container;
    @FXML
    private TableColumn<RendezVous, Void> pdfID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // Set up table columns
            dateID.setCellValueFactory(new PropertyValueFactory<>("date"));
            medecinID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getNom() + " " + cellData.getValue().getMedecin().getPrenom()));
            etatID.setCellValueFactory(new PropertyValueFactory<>("etat"));
            descriptionID.setCellValueFactory(new PropertyValueFactory<>("description"));

            // Populate table with data
            UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
            Utilisateur patient = utilisateurCrud.getPatientById(8);
            RendezVousCrud rendezVousCrud = new RendezVousCrud();
            List<RendezVous> rendezVousList = rendezVousCrud.getRendezVousByUtilisateurId(patient.getId());
            ObservableList<RendezVous> observableList = FXCollections.observableList(rendezVousList);
            tableView.setItems(observableList);

            // Set up column with delete button for each row
            Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>> cellFactory = new Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>>() {
                @Override
                public TableCell<RendezVous, Void> call(final TableColumn<RendezVous, Void> param) {
                    final TableCell<RendezVous, Void> cell = new TableCell<RendezVous, Void>() {

                        private final Button pdfID = new Button("Download");

                        {
                            pdfID.setOnAction((ActionEvent event) -> {
                                RendezVous rendezVous = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Télecharger le rendez-vous informations ");
                                alert.setContentText("Êtes-vous sûr(e) de vouloir télecharger ce rendez-vous  informations  ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {

                                    try {
                                        // Create PDF document
                                        Document document = new Document();
                                        String fileName = rendezVous.getId() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf";
                                        PdfWriter.getInstance(document, new FileOutputStream(fileName));
                                        document.open();

                                        // Add title
                                        Paragraph title = new Paragraph("Rendez-vous Information");
                                        title.setAlignment(Element.ALIGN_CENTER);
                                        document.add(title);
                                        // Add image
                                        Image image = Image.getInstance("src/images/LogoApp.png");
                                        image.setAlignment(Element.ALIGN_CENTER);
                                        document.add(image);
                                        document.add(new Paragraph("Rendez-vous ID: " + rendezVous.getId()));
                                        document.add(new Paragraph("Date: " + rendezVous.getDate()));
                                        document.add(new Paragraph("Médecin: " + rendezVous.getMedecin().getNom() + " " + rendezVous.getMedecin().getPrenom()));
                                        document.add(new Paragraph("État: " + rendezVous.getEtat()));
                                        document.add(new Paragraph("Description: " + rendezVous.getDescription()));
                                        document.close();

                                        // Open PDF file
                                        File file = new File(fileName);
                                        if (file.exists()) {
                                            Desktop.getDesktop().open(file);
                                        } else {
                                            System.out.println("File not found");
                                        }

                                    } catch (DocumentException | FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }

                            });

                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(pdfID);
                            }
                        }
                    };
                    return cell;
                }
            };
            pdfID.setCellFactory(cellFactory);
            tableView.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(ConsulterRdvController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void Tocalendar(ActionEvent event) {
        try {
            Utilisateur patient = new Utilisateur(8);
            displayDoctorCalendar(patient);
        } catch (SQLException ex) {
            Logger.getLogger(ConsulterRdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayDoctorCalendar(Utilisateur patient) throws SQLException {

        RendezVousCrud r = new RendezVousCrud();
        CalendrierCrud cal = new CalendrierCrud();
        List<RendezVous> appointmentsData = r.getAllRendezVousOfPatient(patient.getId());

        // Convert the appointments data to CalendarFX-compatible format
        List<CalendarSource> calendarData = new ArrayList<>();
        CalendarSource appointmentSource = new CalendarSource("Appointments");

        appointmentsData.stream().map((RendezVous appointment) -> {
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
        }).forEach((entry) -> {
            appointmentSource.getCalendars().add(new Calendar("Calendar"));
            appointmentSource.getCalendars().get(appointmentSource.getCalendars().size() - 1).addEntry(entry);
        });

        // Create a new CalendarView and set its calendar sources
        CalendarView calendarView = new CalendarView();
        calendarView.getCalendarSources().addAll(appointmentSource);
        ObservableSet<Entry<?>> selectedEntries = calendarView.getSelections();
        List<RendezVous> selectedAppointments = new ArrayList<>();

        System.out.println("Adding listener to selectedEntries");
        selectedEntries.addListener((SetChangeListener.Change<? extends Entry<?>> c) -> {
            System.out.println("Listener triggered");
            System.out.println("Selected entries: " + selectedEntries);

            if (c.wasRemoved()) {
                System.out.println("Change: " + c);
                Entry<?> deletedEntry = c.getElementRemoved();
                Object userObject = deletedEntry.getUserObject();
                if (userObject instanceof RendezVous) {
                    RendezVous deletedAppointment = (RendezVous) userObject;
                    System.out.println("Deleting appointment with ID: " + deletedAppointment.getId());

                    // Get the appointment's ID and delete it from the database
                    Integer id = deletedAppointment.getId();
                    if (id != null) {
                        try {
                            r.supprimerRdv(id);
                            System.out.println("Appointment with ID " + id + " successfully deleted from the database.");
                        } catch (SQLException ex) {
                            Logger.getLogger(ConsulterRdvController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    System.out.println("User object is not a RendezVous: " + userObject);
                }
            }
        });

        // Create a new stage and set its scene to the calendar view
        Stage stage = new Stage();
        Scene scene = new Scene(calendarView);
        stage.setScene(scene);
        stage.show();

    }
}
