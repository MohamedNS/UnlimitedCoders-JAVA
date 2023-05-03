package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.CommandeProduit;
import Entity.produit;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import Services.CommandeProduitCrud;
import Services.ProduitCrud;

public class AjouterCommandeController implements Initializable {

	@FXML
	private Button annulerButton;

	@FXML
	private Button confirmerButton;

	@FXML
	private TableColumn<produit, String> matriculeCol;

	@FXML
	private TableColumn<produit, String> nomCol;

	@FXML
	private TableColumn<produit, Double> prixCol;

	@FXML
	private TableView<produit> produitsTab;

	@FXML
	private TableColumn<produit, Integer> quantiteCol;

	@FXML
	private TableColumn<produit, String> outName;

	@FXML
	private TableView<produit> outTable;

	@FXML
	private Button removeButton;

	@FXML
	private Button switchBtn;

	ProduitCrud pc;

	CommandeProduitCrud cpc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.pc = new ProduitCrud();
		this.cpc = new CommandeProduitCrud();
		this.populate();

	}

	@FXML
	void annuler(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AfficherCommandes.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void confirmer(ActionEvent event) throws SQLException, IOException {
		List<produit> prods = this.outTable.getItems();
		if (prods.size() != 0) {
			int idCommande = this.cpc.ajouterCommande();
			for (produit produit : prods) {
				CommandeProduit cp = new CommandeProduit();
				cp.setId_commande(idCommande);
				cp.setId_produit(produit.getId());
				cp.setQnt(1);
				this.cpc.addDetails(cp);
			}
			System.out.println("ALL IS DONE");
			this.annuler(event);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Pas de Produits sélectionnés!");
			alert.show();
		}

	}

	public void populate() {
		ObservableList<produit> produits = FXCollections.observableArrayList(this.pc.afficherProduit());
		produitsTab.setItems(produits);
		this.matriculeCol.setCellValueFactory(new PropertyValueFactory<produit, String>("matricule_asseu"));
		this.nomCol.setCellValueFactory(new PropertyValueFactory<produit, String>("nom"));
		this.prixCol.setCellValueFactory(new PropertyValueFactory<produit, Double>("prix"));
	}

	@FXML
	void add(ActionEvent event) {
		if (produitsTab.getSelectionModel().getSelectedItem() != null) {
			outTable.getItems().add(produitsTab.getSelectionModel().getSelectedItem());
			outName.setCellValueFactory(new PropertyValueFactory<produit, String>("nom"));
		}
	}

	@FXML
	void remove(ActionEvent event) {
		if (outTable.getSelectionModel().getSelectedItem() != null) {
			outTable.getItems().remove(outTable.getSelectionModel().getSelectedItem());
		}
	}

}
