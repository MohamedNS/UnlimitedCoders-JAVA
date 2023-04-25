/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.categorie;
import entities.produit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CategorieCrud;
import services.ProduitCrud;

/**
 * FXML Controller class
 *
 * @author rouja
 */
public class AjouterproduitController implements Initializable {

	@FXML
	private Button home;
	@FXML
	private TextField nom;
	@FXML
	private Button ajouterprod;
	@FXML
	private TextField matricule_asseu;
	
	@FXML
	private ChoiceBox<String> listCategories;
	
	@FXML
	private Spinner<Double> prixSpinner;
	
	CategorieCrud categorieCrud;
	
	ProduitCrud produitCrud;
	

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		this.categorieCrud = new CategorieCrud();
		this.produitCrud = new ProduitCrud();
		this.populateCategories();
		SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 9999999);
		this.prixSpinner.setValueFactory(valueFactory);
	}

	@FXML
	private void home(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("afficherproduit.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void ajouterprod(ActionEvent event) throws SQLException, IOException{
		
			produit prod = new produit();
			prod.setCategorie(this.categorieCrud.getCategorieByNom(this.listCategories.getValue()).getId());
			prod.setNom(this.nom.getText());
			prod.setMatricule_asseu(this.matricule_asseu.getText());
			prod.setPrix(String.valueOf(this.prixSpinner.getValue()));
			System.out.println(this.prixSpinner.getValue());
			this.produitCrud.ajouterProduit(prod);
			this.home(event);
		
	}
	
	public void populateCategories() {
		this.listCategories.getItems().clear();
		List<categorie> categories = this.categorieCrud.afficherCategorie();
		for (categorie categorie : categories) {
			this.listCategories.getItems().add(categorie.getNom());
		}
	}

}
