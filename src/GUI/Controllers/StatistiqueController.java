/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Services.RendezVousCrud;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class StatistiqueController implements Initializable {

    @FXML
    private PieChart piechartID;
    @FXML
    private AnchorPane paneID;
    @FXML
    private ComboBox<String> timeRangeComboBox;

    /**
     * Initializes the controller class.
     */
    private ObservableList<PieChart.Data> appointmentsData = FXCollections.observableArrayList();
    @FXML
    private Button backID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeRangeComboBox.getItems().addAll("Week", "Month", "Year");
        timeRangeComboBox.setValue("Week");
        RendezVousCrud rdvc = new RendezVousCrud();

        timeRangeComboBox.setOnAction((event) -> {
            String selectedItem = timeRangeComboBox.getSelectionModel().getSelectedItem();
            switch (selectedItem) {
                case "Week":
                    try {
                        LocalDate now = LocalDate.now();
                        LocalDate monday = now.with(DayOfWeek.MONDAY);
                        int[] appointmentsPerDay = rdvc.getNumAppointmentsPerDay(6, monday);
                        appointmentsData.clear();
                        appointmentsData.addAll(
                                new PieChart.Data("Monday", appointmentsPerDay[0]),
                                new PieChart.Data("Tuesday", appointmentsPerDay[1]),
                                new PieChart.Data("Wednesday", appointmentsPerDay[2]),
                                new PieChart.Data("Thursday", appointmentsPerDay[3]),
                                new PieChart.Data("Friday", appointmentsPerDay[4]),
                                new PieChart.Data("Saturday", appointmentsPerDay[5])
                        );
                        piechartID.setData(appointmentsData);
                        // Calculate and set percentage values
                        int totalAppointments = Arrays.stream(appointmentsPerDay).sum();
                        for (PieChart.Data data : appointmentsData) {
                            double percentage = (double) data.getPieValue() / totalAppointments * 100;
                            data.setName(String.format("%s (%.1f%%)", data.getName(), percentage));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case "Month":
                    try {
                        int[] appointmentsPerWeek = rdvc.getNumAppointmentsPerWeekInMonth(6, LocalDate.now());
                        appointmentsData.clear();
                        appointmentsData.addAll(
                                new PieChart.Data("Week 1", appointmentsPerWeek[0]),
                                new PieChart.Data("Week 2", appointmentsPerWeek[1]),
                                new PieChart.Data("Week 3", appointmentsPerWeek[2]),
                                new PieChart.Data("Week 4", appointmentsPerWeek[3])
                        );
                        piechartID.setData(appointmentsData);
                        // Calculate and set percentage values
                        int totalAppointments = Arrays.stream(appointmentsPerWeek).sum();
                        for (PieChart.Data data : appointmentsData) {
                            double percentage = (double) data.getPieValue() / totalAppointments * 100;
                            data.setName(String.format("%s (%.1f%%)", data.getName(), percentage));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case "Year":
                    try {
                        int[] appointmentsPerMonth = rdvc.getNumAppointmentsPerMonthInYear(6, LocalDate.now().getYear());
                        appointmentsData.clear();
                        appointmentsData.addAll(
                                new PieChart.Data("January", appointmentsPerMonth[0]),
                                new PieChart.Data("February", appointmentsPerMonth[1]),
                                new PieChart.Data("March", appointmentsPerMonth[2]),
                                new PieChart.Data("April", appointmentsPerMonth[3]),
                                new PieChart.Data("May", appointmentsPerMonth[4]),
                                new PieChart.Data("June", appointmentsPerMonth[5]),
                                new PieChart.Data("July", appointmentsPerMonth[6]),
                                new PieChart.Data("August", appointmentsPerMonth[7]),
                                new PieChart.Data("September", appointmentsPerMonth[8]),
                                new PieChart.Data("October", appointmentsPerMonth[9]),
                                new PieChart.Data("November", appointmentsPerMonth[10]),
                                new PieChart.Data("December", appointmentsPerMonth[11])
                        );
                        piechartID.setData(appointmentsData);
                        // Calculate and set percentage values
                        int totalAppointments = Arrays.stream(appointmentsPerMonth).sum();
                        for (PieChart.Data data : appointmentsData) {
                            double percentage = (double) data.getPieValue() / totalAppointments * 100;
                            data.setName(String.format("%s (%.1f%%)", data.getName(), percentage));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                default:
                    break;
            }
        });

    }

    @FXML
    private void backToMainPage(ActionEvent event) {
        try {
            Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Medecin.fxml"));
            Scene mainPageScene = new Scene(mainPageParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
