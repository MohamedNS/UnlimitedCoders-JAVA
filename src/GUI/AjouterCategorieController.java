package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CategorieCrud;

public class AjouterCategorieController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea descText;

    @FXML
    private TextField nomText;
    
    CategorieCrud categorieCrud;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.categorieCrud = new CategorieCrud();
		
	}

    @FXML
    void cancel(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("AfficherCategories.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void confirmAdd(ActionEvent event) throws IOException {
    	categorie cat = new categorie();
    	cat.setNom(this.nomText.getText());
    	cat.setDescription(this.descText.getText());
    	this.categorieCrud.ajouterCategorie(cat);
    	this.cancel(event);
    }

	

}
