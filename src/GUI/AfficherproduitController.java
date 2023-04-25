/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.produit;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ProduitCrud;

/**
 * FXML Controller class
 *
 * @author rouja
 */
public class AfficherproduitController implements Initializable {

    @FXML
    private TableView<produit> tabProduit;
    
    @FXML
    private TableColumn<produit, Integer> colCategorie;

    @FXML
    private TableColumn<produit, String> colMatricule;

    @FXML
    private TableColumn<produit, String> colNom;

    @FXML
    private TableColumn<produit, String> colPrix;
    
    @FXML
    private Button homeButton;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button addButton;
    
    ProduitCrud pc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	this.pc = new ProduitCrud();
    	this.afficherProduit();
    	
    }
    
    @FXML
    private Button afficherproduit;
    @FXML
    private Button Actualiser;

    @FXML
    private void home(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    
    public void afficherProduit() {
        System.out.println("Click afficher");
        ObservableList<produit> produits = FXCollections.observableArrayList(this.pc.afficherProduit());
        System.out.println(produits);
        tabProduit.setItems(produits);
        colNom.setCellValueFactory(new PropertyValueFactory<produit, String>("nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory<produit, String>("prix"));
//        colCategorie.setCellValueFactory(new PropertyValueFactory<produit, Integer>("categorie_id"));
        
        colMatricule.setCellValueFactory(new PropertyValueFactory<produit, String>("matricule_asseu"));
    }
    
    
    
    @FXML
    public void Actualiser(ActionEvent event)
    {
    	afficherProduit();
    }
    
    public void setIcons() {
    	try {
    		Image homeIcon = new Image(getClass().getResourceAsStream("/javafx/scene/control/images/home.png"));
        	ImageView iv = new ImageView(homeIcon);
        	homeButton.setGraphic(iv);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    public void addScreen(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("ajouterproduit.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /*
     
     FXMLLoader loader = new FXMLLoader(getClass().getResource("LogEchangeIn.fxml"));
		loader.setControllerFactory(
				c -> new LogController(currentUserId));
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		this.mainPane.setDisable(true);
		stage.showAndWait();
		this.mainPane.setDisable(false);
     */
    
    
    
    @FXML
    public void updateScreen(ActionEvent event) throws IOException {
    	produit selectedItem = this.tabProduit.getSelectionModel().getSelectedItem();
    	if(selectedItem != null) {
    		System.out.println(selectedItem);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateProduit.fxml"));
    		loader.setControllerFactory(c -> new UpdateProduitController(selectedItem));
    		Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText("Erreur");
    		alert.setContentText("Veuillez sélectionner un produit pour le modifier!");
    		alert.showAndWait();
    	}
    }
    
    @FXML
    public void delete(ActionEvent event) throws IOException {
    	produit selectedItem = this.tabProduit.getSelectionModel().getSelectedItem();
    	if(selectedItem != null) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Cofirmation");
    		alert.setHeaderText("Êtes-vous sûr?");
    		alert.setContentText("Cette Action est irreversible");
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			//DELETE IT
    			System.out.println("DELETING IT!");
    			this.pc.supprimerProduit(selectedItem);
    			System.out.println("DELETED!");
    			this.afficherProduit();
    			alert.close();
    		}else {
    			alert.close();
    		}
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erreur");
    		alert.setHeaderText("Erreur");
    		alert.setContentText("Veuillez sélectionner un produit pour le modifier!");
    		alert.showAndWait();
    	}
    }
    

}
