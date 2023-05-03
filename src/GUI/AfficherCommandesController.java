package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.DetailsCommandeProduit;
import Entity.commande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Services.CommandeProduitCrud;

public class AfficherCommandesController implements Initializable{

    @FXML
    private TableColumn<commande, Double> commandePrixTotalCol;

    @FXML
    private TableView<commande> commandesTab;

    @FXML
    private TableColumn<commande, Date> dateAjoutCol;

    @FXML
    private TableColumn<DetailsCommandeProduit, Double> detailsPrixTotalCol;

    @FXML
    private TableColumn<DetailsCommandeProduit, String> detailsProduitCol;

    @FXML
    private TableColumn<DetailsCommandeProduit, Double> detailsProduitUnitaireCol;

    

    @FXML
    private TableView<DetailsCommandeProduit> detailsTab;
    
    CommandeProduitCrud commandeProduitCrud;
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.commandeProduitCrud = new CommandeProduitCrud();
		this.detailsTab.setVisible(false);
		try {
			this.populateLeft();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    

    @FXML
    void getDetails(MouseEvent event) throws SQLException {
    	System.out.println(this.commandesTab.getSelectionModel().getSelectedItem().getId());
    	this.detailsTab.setVisible(true);
    	int cmd_id = this.commandesTab.getSelectionModel().getSelectedItem().getId();
    	ObservableList<DetailsCommandeProduit> commandes = FXCollections.observableArrayList(this.commandeProduitCrud.detailsCommande(cmd_id));
    	this.detailsTab.setItems(commandes);
    	this.detailsPrixTotalCol.setCellValueFactory(new PropertyValueFactory<DetailsCommandeProduit, Double>("prixTotal"));
    	this.detailsProduitCol.setCellValueFactory(new PropertyValueFactory<DetailsCommandeProduit, String>("nomProduit"));
    	this.detailsProduitUnitaireCol.setCellValueFactory(new PropertyValueFactory<DetailsCommandeProduit, Double>("prixUnitaire"));
    	
    }
    
    void populateLeft() throws SQLException {
    	ObservableList<commande> commandes = FXCollections.observableArrayList(this.commandeProduitCrud.getCommandes());
    	this.commandesTab.setItems(commandes);
    	this.dateAjoutCol.setCellValueFactory(new PropertyValueFactory<commande, Date>("date"));
    	this.commandePrixTotalCol.setCellValueFactory(new PropertyValueFactory<commande, Double>("prix"));
    }
    
    @FXML
    private void addScreen(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("AjouterCommande.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }



	

}
