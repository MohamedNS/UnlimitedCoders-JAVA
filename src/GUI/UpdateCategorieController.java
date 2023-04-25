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

public class UpdateCategorieController implements Initializable {

	@FXML
	private Button annulerButton;

	@FXML
	private TextArea descText;

	@FXML
	private Button modifierButton;

	@FXML
	private TextField nomText;

	CategorieCrud categorieCrud;

	categorie cat;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.categorieCrud = new CategorieCrud();
		this.nomText.setText(this.cat.getNom());
		this.descText.setText(this.cat.getDescription());
	}

	public UpdateCategorieController(categorie cat) {
		this.cat = cat;
	}

	@FXML
	void annuler(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AfficherCategories.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void modifier(ActionEvent event) throws IOException {
		categorie cat_updated = new categorie();
		cat_updated.setId(this.cat.getId());
		cat_updated.setNom(this.nomText.getText());
		cat_updated.setDescription(this.descText.getText());
		this.categorieCrud.modifierCategorie(cat_updated.getId(), cat_updated.getNom(), cat_updated.getDescription());
		this.annuler(event);
	}

}
