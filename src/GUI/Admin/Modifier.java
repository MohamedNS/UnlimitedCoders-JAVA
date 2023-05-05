package GUI.Admin;

import Services.ArticleService;
import Entity.Article;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Modifier implements Initializable {

    @FXML
    private TextArea titre;
    @FXML
    private TextArea descP;
    @FXML
    private DatePicker date;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab healthCondTab;
    @FXML
    private Button backID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void modifier(javafx.event.ActionEvent actionEvent) {
        Article a = new Article();

        String nom = titre.getText();
        String description = descP.getText();
        LocalDate numTel = date.getValue();
        java.util.Date utilDate = java.sql.Date.valueOf(numTel);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        if (nom.isEmpty() || description.isEmpty()) {
            // Display an error message if any field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add("/resources/styles.css");
            dialogPane.setMinWidth(400);
            dialogPane.setMinHeight(200);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        ArticleService ar = new ArticleService();
        a.setTitre(nom);
        a.setArticle_desc(description);
        a.setArticle_date(sqlDate);
        ar.update(a);

        // Display a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("L'ONG a été Modifie avec succès.");
        alert.showAndWait();
    }
    int idart;
    Article a;

    public void setId(Article g) {
        System.out.println(g);
        idart = g.getId();
        this.a = g;
        titre.setText(g.getTitre());
        descP.setText(g.getArticle_desc());

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
