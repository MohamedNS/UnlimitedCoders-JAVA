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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Services.ProduitCrud;

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
    
    @FXML
    private TextField searchBar;
    
    @FXML
    private Button exportEx;
     @FXML
    private Button importEx;

    
    ProduitCrud pc;
    @FXML
    private Button refreshButton;
    @FXML
    private Button btnqr;

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
    
    @FXML
    void search(KeyEvent event) {
    	if(this.searchBar.getText().length() == 0) {
    		this.afficherProduit();
    	}else {
    		ObservableList<produit> produits = FXCollections.observableArrayList(this.pc.afficherProduitByKey(this.searchBar.getText()));
            System.out.println(produits);
            tabProduit.setItems(produits);
            colNom.setCellValueFactory(new PropertyValueFactory<produit, String>("nom"));
            colPrix.setCellValueFactory(new PropertyValueFactory<produit, String>("prix"));
            colMatricule.setCellValueFactory(new PropertyValueFactory<produit, String>("matricule_asseu"));
    	}
    }
    
    @FXML
    private void exporterEx(ActionEvent event) {
   ProduitCrud ac = new ProduitCrud();
    ObservableList<produit> produits = FXCollections.observableArrayList(ac.afficherProduit());
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("Details Produit");
    XSSFRow header = sheet.createRow(0);
    header.createCell(0).setCellValue("Categorie id");
    header.createCell(1).setCellValue("Nom Produit");
    header.createCell(2).setCellValue("Matricule asseu");
    header.createCell(3).setCellValue("Prix");
   
   
    int rowNum = 1;
    for (produit produit : produits) {
        XSSFRow row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(produit.getCategorie());
        row.createCell(1).setCellValue(produit.getNom());
        row.createCell(2).setCellValue(produit.getMatricule_asseu());
        row.createCell(3).setCellValue(produit.getPrix());
    }

    try {
        FileOutputStream fileOut = new FileOutputStream("Produit.xlsx");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Excel");
        alert.setHeaderText("Exportation terminée");
        alert.setContentText("La table produit a été exportée avec succès en une table Excel.");
        alert.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    @FXML
private void importerEx(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
        try {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // skip header row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                produit produit = new produit();
                row.createCell(0).setCellValue(produit.getCategorie());
        row.createCell(1).setCellValue(produit.getNom());
        row.createCell(2).setCellValue(produit.getMatricule_asseu());
        row.createCell(3).setCellValue(produit.getPrix());
                ProduitCrud ac = new ProduitCrud();
                ac.ajouterProduit(produit);
            }

            workbook.close();
            fis.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Import Excel");
            alert.setHeaderText("Importation terminée");
            alert.setContentText("Les données ont été importées avec succès depuis le fichier Excel.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    @FXML
    private void qr(MouseEvent event) {
        Stage qrStage = new Stage();
            produit p;
        
        p=tabProduit.getSelectionModel().getSelectedItem();
        ProduitCrud pd=new ProduitCrud();
        //pd.Qr(qrStage,p);
    }


}