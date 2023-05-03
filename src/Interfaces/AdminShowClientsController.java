/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Entity.User;
import Services.ServiceUser;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shayma
 */
public class AdminShowClientsController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Label username;
    @FXML
    private Button home_btn;
    @FXML
    private Button add_agent_btn;
    @FXML
    private FontAwesomeIcon logout;
    @FXML
    private AnchorPane addagent_form;
    @FXML
    private TableView<User> table_view;
    @FXML
    private TextField search;
    private Button add_btn;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> mail;
    @FXML
    private TableColumn<?, ?> telephone;
    @FXML
    private TableColumn<?, ?> banned;
    @FXML
    private TextField tnom;
    @FXML
    private TextField tprenom;
    @FXML
    private TextField temail;
    @FXML
    private TextField ttelephone;
    @FXML
    private Button debloque_btn;
    @FXML
    private Button bloque_btn;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addUserShowListData();
        addEmployeeSearch();
    } 
    
    
    @FXML
        public void UserSelect() {
        User tab = table_view.getSelectionModel().getSelectedItem();
        int num = table_view.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        
        tnom.setText(String.valueOf(tab.getNom()));
        tprenom.setText(String.valueOf(tab.getPrenom()));
        temail.setText(String.valueOf(tab.getEmail()));
        ttelephone.setText(String.valueOf(tab.getTelephone()));
    }
    

    private ObservableList<User> addUsersList;
    public void addUserShowListData() {
        ServiceUser su = new ServiceUser();
        addUsersList = su.afficherlesUsers();

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        banned.setCellValueFactory(new PropertyValueFactory<>("isbanned"));
       
        

        table_view.setItems(addUsersList);

    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void home(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void addagent(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("AddAgent.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void showusers(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("AdminDashbord.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
    

    @FXML
    private void showclients(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("AdminShowClients.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
    
        public void addEmployeeSearch() {

        FilteredList<User> filter = new FilteredList<>(addUsersList, e -> true);

        search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateUserData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateUserData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getNom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getPrenom().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getEmail().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateUserData.getTelephone().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<User> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(table_view.comparatorProperty());
        table_view.setItems(sortList);
    }

    @FXML
    private void DebloqueUser(MouseEvent event) {
        try{
             
                User bd_tab = table_view.getSelectionModel().getSelectedItem();
                int idUser =bd_tab.getId();
                User bd= new User(idUser);
                ServiceUser BC = new ServiceUser();
                BC.DeBan(bd);
                addUserShowListData(); 
        
        }
                catch (Exception e) {
        }
    }

    @FXML
    private void BloqueUser(MouseEvent event) {
        try{
             
                User bd_tab = table_view.getSelectionModel().getSelectedItem();
                int idUser =bd_tab.getId();
                User bd= new User(idUser);
                ServiceUser BC = new ServiceUser();
                BC.Ban(bd);
                addUserShowListData(); 
        
        }
                catch (Exception e) {
        }
    }
}
