/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.DocumentException;
import entities.Ordonnance;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceOrdonnance;
import services.ServicePDF;
import util.Connexion;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AfficheOrdonnanceController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private TextField idcons;
    @FXML
    private TableView<Ordonnance> ordonnanceTable;
    @FXML
    private TableColumn<Ordonnance, ?> idColonne;
    @FXML
    private TableColumn<Ordonnance, ?> consultationColonne;
    @FXML
    private TableColumn<Ordonnance, ?> validiteColonne;
    @FXML
    private TableColumn<?, ?> medicamentColonne;
    @FXML
    private Button btnPdf;
Connection cnx;
 private final ObservableList<Ordonnance> dataList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> codeColonne;
    @FXML
    private Button next;
       
    /**
     * Initializes the controller class.
     */
    
    public ObservableList<Ordonnance> getOrdonnances()
    {
        ObservableList<Ordonnance> observableListOrdonnance = FXCollections.observableArrayList();
        ServiceOrdonnance sv = new ServiceOrdonnance();
        List<Ordonnance> listeOrdonnances = sv.afficherOrdonnance();
        for(Ordonnance ordonnance: listeOrdonnances)
        {
            System.out.println("ordonnance Affichage : "+ordonnance);
            observableListOrdonnance.add(ordonnance);
        }
        return observableListOrdonnance;
    }

   
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Ordonnance> list = getOrdonnances();
        idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
        consultationColonne.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
        validiteColonne.setCellValueFactory(new PropertyValueFactory<>("validite"));
        medicamentColonne.setCellValueFactory(new PropertyValueFactory<>("nomMedicaments"));
         codeColonne.setCellValueFactory(new PropertyValueFactory<>("code"));
        
         
		ordonnanceTable.setItems(list);
                String value =idcons.getText();
		FilteredList<Ordonnance> filteredTable = new FilteredList<>(list,b->true);
		code.textProperty().addListener((observable,oldValue,newValue)->{
			filteredTable.setPredicate(rec->{
				if(newValue == null || newValue.isEmpty())
				{
					return true;
				}
				String lowerCasefilter = newValue.toLowerCase();
				if(rec.getCode().toLowerCase().indexOf(newValue) != -1 ) 
				{
					return true;
				}
				else
					return false;
				
			});
		});
		SortedList<Ordonnance> sortedList = new SortedList<>(filteredTable);
		sortedList.comparatorProperty().bind(ordonnanceTable.comparatorProperty());
		ordonnanceTable.setItems(sortedList);
        // TODO
    }    

    @FXML
   private void btnPdf(ActionEvent evt) throws FileNotFoundException, DocumentException, IOException
    {
        System.out.println("Boutton PDF Click");
        ServiceOrdonnance sv = new ServiceOrdonnance();
        ServicePDF pdf = new ServicePDF();
        List<Ordonnance> listeOrdonnances = sv.afficherOrdonnance();
        pdf.genererPdfOrdonnance("Ordonnance", listeOrdonnances);
    }

 
    public void show()
    {
        try{
            
            System.out.println("Consultation Click");
            Parent loader = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }

    @FXML
    private void btnnext(ActionEvent event) {
          Stage stage = (Stage) next.getScene().getWindow();
       
        facteur mc = new facteur();
        mc.show();
    }
}

// Set up filtered list
    