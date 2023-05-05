package GUI.Admin;

import Services.ArticleService;
import Entity.Article;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class AjouterArticle implements Initializable {

    @FXML
    private javafx.scene.control.TextArea titre;
    @FXML
    private javafx.scene.control.TextArea descP;
    @FXML
    private DatePicker date;


ArticleService articleDao=new ArticleService();
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab healthCondTab;
    @FXML
    private javafx.scene.control.Button backID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
    @FXML
    private void Ajouter() {



        if (!validateInput()) {
            return;
        }
        else {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = date.getValue();

            java.util.Date utilDate = java.sql.Date.valueOf(localDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Article a = new Article(titre.getText(),descP.getText() ,sqlDate) ;
            articleDao.insertArticle(a);

            System.out.println("addeddd2 "); }
    }


    private  boolean validateInput() {


        if (descP.getText().isEmpty()) {
            showAlert("Description field cannot be empty.");
            return false;
        }


        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error check your inputs");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void BackToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Admin/AdminMain.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }

}



















