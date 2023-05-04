/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import Services.AssuranceCRUD;
import Services.DepotCRUD;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ChartsFXMLController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btnRetour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        borderPane.setCenter(buildBarChart());
    }

    @FXML
    private void handleShowBarChart(ActionEvent event) {
        borderPane.setCenter(buildBarChart());
    }
    
    
    /**
     * 
     * @return 
     */
    private BarChart buildBarChart(){
    CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Assurance");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Patient");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Repartition des patients par assurance");

        AssuranceCRUD assuranceCRUD = new AssuranceCRUD();
        Map<String, Number> patientsByAssurance = assuranceCRUD.getPatientsByAssurance();
        for (Map.Entry<String, Number> entry : patientsByAssurance.entrySet()) {
            data.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(data);

        // Create tooltip to show patient count
        final Tooltip tooltip = new Tooltip();
        tooltip.setAutoHide(false);
        tooltip.setContentDisplay(ContentDisplay.RIGHT);

        for (final XYChart.Data<String, Number> d : data.getData()) {
            Tooltip.install(d.getNode(), tooltip);
            d.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tooltip.setText(String.format("%d patients", d.getYValue()));
                    tooltip.show(d.getNode(), event.getScreenX(), event.getScreenY());
                }
            });
            d.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tooltip.hide();
                }
            });
        }
        return barChart;
        
    }

    @FXML
    private void handleShowPieChart(ActionEvent event) {
        // Get data from database
        DepotCRUD depotCRUD = new DepotCRUD();
        Map<String, Integer> depositsByState = depotCRUD.getDepositsByState();

        // Create pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : depositsByState.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Create pie chart
        final PieChart pieChart = new PieChart(pieChartData);

        pieChart.setTitle("Répartition des dépôts par état");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);

        // Create tooltip to show deposit count
        final Tooltip tooltip = new Tooltip();
        tooltip.setAutoHide(false);
        tooltip.setContentDisplay(ContentDisplay.RIGHT);

        for (final PieChart.Data data : pieChartData) {
            Tooltip.install(data.getNode(), tooltip);
            data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tooltip.setText(String.format("%d dépôts", (int) data.getPieValue()));
                    tooltip.show(data.getNode(), event.getScreenX(), event.getScreenY());
                }
            });
            data.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    tooltip.hide();
                }
            });
        }

        borderPane.setCenter(pieChart);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleUpdateData(ActionEvent event) {
    }

    @FXML
    public void btnRetour(ActionEvent evt) {
        System.out.println("Retour Click");
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        stage.close();
        MenuFXMLController mc = new MenuFXMLController();
        mc.show();
    }


}
