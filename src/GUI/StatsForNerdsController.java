package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Entity.custom.BarChartData;
import Entity.custom.LineChartData;
import Entity.custom.PieChartData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import Services.ChartsQueries;

public class StatsForNerdsController implements Initializable {
	

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private LineChart<String, Integer> lineChart;
    
    @FXML
    private CategoryAxis barX;

    @FXML
    private NumberAxis barY;

    @FXML
    private CategoryAxis lineX;

    @FXML
    private NumberAxis lineY;

    @FXML
    private PieChart pieChart;
    
    @FXML
    private Button btnAcceuil;
    
    
    
    ChartsQueries chartsQueries;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.chartsQueries = new ChartsQueries();
		
		try {
			this.init_pie();
			this.init_bar();
			this.init_line();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void init_pie() throws SQLException {
		List<PieChart.Data> pieChartData = new ArrayList<>();
		for (PieChartData pcd : chartsQueries.pieChartData()) {
			pieChartData.add(new PieChart.Data(pcd.getCategory(), pcd.getProducts()));
		}
		this.pieChart.setData(FXCollections.observableArrayList(pieChartData));
		pieChart.setTitle("Produits par Catégories");
	}
	
	public void init_bar() throws SQLException {
		ObservableList<Series<String, Integer>> barChartData = FXCollections.observableArrayList();
		Series<String, Integer> series = new Series<>();
		for (BarChartData bcd : this.chartsQueries.barChartData()) {
			series.getData().add(new XYChart.Data<>(bcd.getProduct(),bcd.getNumber()));
		}
		barChartData.add(series);
		barChart.setData(barChartData);
		barChart.setTitle("Top 3 Produits Commandés");
		barY.setLabel("Commandes");
		barX.setLabel("Produits");
		barY.setTickLabelFormatter(new StringConverter<Number>() {
			
			@Override
			public String toString(Number arg0) {
				return String.format("%.0f", arg0);
			}
			
			@Override
			public Number fromString(String arg0) {
				return null;
			}
		});
	}
	
	public void init_line() throws SQLException {
		ObservableList<Series<String, Integer>> lineChartData = FXCollections.observableArrayList();
		Series<String, Integer> series = new Series<>();
		for (LineChartData lcd : this.chartsQueries.lineChartData()) {
			series.getData().add(new XYChart.Data<>(lcd.getDate().toString(),lcd.getNumber()));
		}
		lineChartData.add(series);
		lineChart.setData(lineChartData);
		lineChart.setTitle("Commandes par Jour");
		lineY.setLabel("Commandes");
		lineX.setLabel("Jour");
		lineY.setTickLabelFormatter(new StringConverter<Number>() {
			
			@Override
			public String toString(Number arg0) {
				return String.format("%.0f", arg0);
			}
			
			@Override
			public Number fromString(String arg0) {
				return null;
			}
		});
	}
	
	
	@FXML
    void goBack(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
	
	

}
