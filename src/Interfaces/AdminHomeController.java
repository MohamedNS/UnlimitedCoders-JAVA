/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Utils.MyConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shayma
 */
public class AdminHomeController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Label username;
    @FXML
    private Button home_btn;
    @FXML
    private Button add_agent_btn;
    @FXML
    private Button salary_btn;
    @FXML
    private FontAwesomeIcon logout;
    @FXML
    private AnchorPane home_form;
    @FXML
    private Label home_totalagent;
    @FXML
    private Label home_totalpresent;
    @FXML
    private Label home_totalclient;
    @FXML
    private BarChart<String, Integer> home_chart;
    @FXML
    private AnchorPane home_form1;

   
    private ResultSet result;
    private ResultSet result1;
    private ResultSet result2;
    private ResultSet result3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeTotalEmployees();
        homeTotalEmployees1();
        homeTotalEmployees2();
        homeChart();
    }

    public void homeTotalEmployees() {

        String sql = "SELECT COUNT(id) FROM user WHERE `roles` = '[\"ROLE_AGENT\"]'";

        int countData = 0;
        try {

            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(sql);
            result1 = pst.executeQuery();

            while (result1.next()) {
                countData = result1.getInt("COUNT(id)");
            }

            home_totalagent.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeTotalEmployees1() {

        String sql = "SELECT COUNT(id) FROM user WHERE `roles` = '[\"ROLE_USER\"]'";

        int countData = 0;
        try {

            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(sql);
            result = pst.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            home_totalclient.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeTotalEmployees2() {

        String sql = "SELECT COUNT(id) FROM user WHERE isbanned ='0'";

        int countData = 0;
        try {

            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(sql);
            result2 = pst.executeQuery();

            while (result2.next()) {
                countData = result2.getInt("COUNT(id)");
            }

            home_totalpresent.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeChart() {

        home_chart.getData().clear();

        String sql = "SELECT created_at, COUNT(id) FROM user GROUP BY created_at ORDER BY TIMESTAMP(created_at) ASC LIMIT 7";

        try {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

           

            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(sql);
            result3 = pst.executeQuery();

            while (result3.next()) {
                System.out.println(result3.getString(1));
                System.out.println(result3.getInt(2));
                String b= result3.getString(1);
                int a = result3.getInt(2);
                series.getData().add(new XYChart.Data<>(b, a));
            }

            home_chart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @FXML
    private void logout(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/login.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void home(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/AdminHome.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void addagent(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/AddAgent.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void showusers(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/AdminDashbord.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void showclients(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/AdminShowClients.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
    
}
