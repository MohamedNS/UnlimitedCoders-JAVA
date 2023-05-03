package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Entity.categorie;
import Entity.produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import Services.CategorieCrud;
import Services.ProduitCrud;

public class UpdateProduitController implements Initializable {

	@FXML
	private Button annulerButton;

	@FXML
	private Button confirmButton;

	@FXML
	private TextField fieldMatricule;

	@FXML
	private TextField fieldNom;

	@FXML
	private ChoiceBox<String> listCategories;

	@FXML
	private Spinner<Double> spinnerPrix;

	produit prod;

	CategorieCrud categorieCrud;

	ProduitCrud produitCrud;

	// CONSTRUCTOR TO RETRIEVE DATA
	public UpdateProduitController(produit prod) {
		this.prod = prod;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.categorieCrud = new CategorieCrud();
		this.produitCrud = new ProduitCrud();
		this.populateCategories();
		try {
			this.populateFields();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void populateCategories() {
		this.listCategories.getItems().clear();
		List<categorie> categories = this.categorieCrud.afficherCategorie();
		for (categorie categorie : categories) {
			this.listCategories.getItems().add(categorie.getNom());
		}
	}

	public void populateFields() throws SQLException {
		this.fieldMatricule.setText(this.prod.getMatricule_asseu());
		this.fieldNom.setText(this.prod.getNom());
		this.listCategories.setValue(this.categorieCrud.getCategorieById(this.prod.getCategorie()).getNom());
		this.spinnerPrix.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 9999999));
		this.spinnerPrix.getValueFactory().setValue(Double.parseDouble(this.prod.getPrix()));
	}

	@FXML
	public void update(ActionEvent event) throws SQLException, IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Êtes-vous sûr?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			produit prod_update = new produit();
			prod_update.setId(this.prod.getId());
			prod_update.setCategorie(this.categorieCrud.getCategorieByNom(this.listCategories.getValue()).getId());
			prod_update.setNom(this.fieldNom.getText());
			prod_update.setMatricule_asseu(this.fieldMatricule.getText());
			prod_update.setPrix(String.valueOf(this.spinnerPrix.getValue()));
			this.produitCrud.modifierProduit(prod_update);
			alert.close();
			Parent root = FXMLLoader.load(getClass().getResource("afficherproduit.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			alert.close();
		}
	}

}
