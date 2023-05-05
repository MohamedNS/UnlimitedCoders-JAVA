/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.produit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Services.ProduitCrud;

/**
 * FXML Controller class
 *
 * @author rouja
 */
public class HomeController implements Initializable {

	@FXML
	private Button ajouterproduit;
	@FXML
	private Button ajoutercat;
	@FXML
	private Button ajoutercomm;
	@FXML
	private Button afficherproduit;

	@FXML
	private Button bntRetour;
	
	@FXML
    private Button statsForNerds;

	@FXML
	private Button afficherproduit1;

	@FXML
	private Button afficherCommande;

	@FXML
	private TableView<produit> tabProduit;
	@FXML
	private TableColumn<?, ?> colCategorie;

	@FXML
	private TableColumn<?, ?> colMatricule;

	@FXML
	private TableColumn<?, ?> colNom;

	@FXML
	private TableColumn<?, ?> colPrix;
	private ActionEvent evt;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO

		// afficherproduit();
	}

	@FXML
	private void ajouterproduit(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ajouterproduit.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void ajoutercat(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ola.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void ajoutercomm(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ajouterComm.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void afficherproduit() {
		System.out.println("Click afficher");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("afficherproduit.fxml"));
			Stage stage = (Stage) afficherproduit.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void afficher() {
		ActionEvent evt = null;
		// this.afficherproduit(evt);
	}

	@FXML
	private void affichercommande(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AfficherCommandes.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void afficherCategories(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AfficherCategories.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
    void goToStats(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StatsForNerds.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
	@FXML
	public void btnRetour(ActionEvent evt)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../GUI/MenuBienvenu.fxml"));
		try{
			loader.load();
			Parent root = loader.getRoot();
			Stage stage = (Stage) ajouterproduit.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
		catch(IOException ex)
		{
			System.err.println(ex.getMessage());
		}

	}

}
