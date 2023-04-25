package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.categorie;
import entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.CategorieCrud;

public class AfficherCategoriesController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button addButton;

	@FXML
	private TableView<categorie> categoriesTable;

	@FXML
	private Button deleteButton;

	@FXML
	private TableColumn<categorie, String> descColumn;

	@FXML
	private Button homeButton;

	@FXML
	private TableColumn<categorie, Integer> idColumn;

	@FXML
	private TableColumn<categorie, String> nomColumn;

	@FXML
	private Button updateButton;

	CategorieCrud categorieCrud;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.categorieCrud = new CategorieCrud();
		this.populateTable();
	}

	@FXML
	void addScreen(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AjouterCategorie.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void delete(ActionEvent event) throws IOException {
		categorie selectedItem = this.categoriesTable.getSelectionModel().getSelectedItem();
		if(selectedItem != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Cofirmation");
    		alert.setHeaderText("Êtes-vous sûr?");
    		alert.setContentText("Cette Action est irreversible! Elle peut affecter négativement des produits déja enregistrés!");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			this.categorieCrud.supprimerCategorie(selectedItem.getId());
    			this.homeScreen(event);
    			alert.close();
    		}else {
    			alert.close();
    		}
		}else {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText("Erreur");
    		alert.setContentText("Veuillez sélectionner une catégorie pour la supprimer!");
    		alert.showAndWait();
		}
	}

	@FXML
	void homeScreen(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void updateScreen(ActionEvent event) throws IOException {
		categorie selectedItem = this.categoriesTable.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			System.out.println(selectedItem);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCategorie.fxml"));
			loader.setControllerFactory(c -> new UpdateCategorieController(selectedItem));
			Parent root = loader.load();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Erreur");
			alert.setContentText("Veuillez sélectionner une catégorie pour la modifier!");
			alert.showAndWait();
		}
	}

	public void populateTable() {
		ObservableList<categorie> categories = FXCollections
				.observableArrayList(this.categorieCrud.afficherCategorie());
		this.categoriesTable.setItems(categories);
		this.idColumn.setCellValueFactory(new PropertyValueFactory<categorie, Integer>("id"));
		this.nomColumn.setCellValueFactory(new PropertyValueFactory<categorie, String>("nom"));
		this.descColumn.setCellValueFactory(new PropertyValueFactory<categorie, String>("description"));

	}

}
