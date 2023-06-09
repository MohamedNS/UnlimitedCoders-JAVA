/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import Entity.Facteur;
import Entity.Ordonnance;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Services.FacteurGrud;
import static Services.FacteurGrud.cnx2;
import Services.ServiceExcel;
import Services.ServiceOrdonnance;
import Services.ServicePDF;
import Utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AfficherfacturController implements Initializable {

    @FXML
    private TextField tfcin;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprnom;
    @FXML
    private TextField tfidpatient;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tfnommed;
    @FXML
    private TextField tfidmed;
    @FXML
    private Button tfupdate;
    @FXML
    private Button tffdelet;
    @FXML
    private TextField tfdosage;
    @FXML
    private TableColumn<Facteur, ?> tbid;
    @FXML
    private TableColumn<Facteur, ?> tbcin;
    @FXML
    private TableColumn<Facteur, ?> tbnom;
    @FXML
    private TableColumn<Facteur, ?> tbprnom;
    @FXML
    private TableColumn<Facteur, ?> tbidpatient;
    @FXML
    private TableColumn<Facteur, ?> tbidmed;
    @FXML
    private TableColumn<Facteur, ?> tbnommed;
    @FXML
    private TableColumn<Facteur, ?> tbdosage;
    @FXML
    private TableView<Facteur> tbfactur;
    ObservableList<Facteur> List = FXCollections.observableArrayList();
    Integer index;
    public static Connection cnx2;
    @FXML
    private TableColumn<Facteur, ?> tbprix;
    @FXML
    private TextField tfid;
    @FXML
    private Button pdf;
    @FXML
    private Button upload;
    @FXML
    private Button next;
    /**
     * Initializes the controller class.
     */
       public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultation");
        alert.setHeaderText(null);
        alert.setContentText("Operation "+operation+" Effectue avec success");
        alert.show();
    }
    public void notifierError(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Operation Refusee");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
    public boolean notifierConfirmation(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,msg,ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public ObservableList<Facteur> getFacteur()
    {
        ObservableList<Facteur> observableListOrdonnance = FXCollections.observableArrayList();
        FacteurGrud sv = new FacteurGrud();
        List<Facteur> listeOrdonnances = sv.afficherfacteurs();
        for(Facteur facteur: listeOrdonnances)
        {
            System.out.println("facteur Affichage : "+facteur);
            observableListOrdonnance.add(facteur);
        }
        return observableListOrdonnance;
    }
    
    public void Update() {
          ObservableList<Facteur> list = getFacteur();
       
        tbid.setCellValueFactory(new PropertyValueFactory<>("id"));
    tbcin.setCellValueFactory(new PropertyValueFactory<>("cin"));
    tbnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    tbprnom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    
   tbidpatient.setCellValueFactory(new PropertyValueFactory<>("id_patient"));
   tbidmed.setCellValueFactory(new PropertyValueFactory<>("id_medicament"));
     tbnommed.setCellValueFactory(new PropertyValueFactory<>("nom_med"));
    tbdosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
    tbprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tbfactur.setItems(list);
    }
    public void initialize(URL url, ResourceBundle rb) {
        Update();
        // TODO
    }    

    @FXML
    private void getItem(MouseEvent event) {
        index = tbfactur.getSelectionModel().getSelectedIndex();
        if(index<= -1){
        
        return;
        }
        tfid.setText(tbid.getCellData(index).toString());
        tfcin.setText(tbcin.getCellData(index).toString());
        tfnom.setText(tbnom.getCellData(index).toString());
        tfprnom.setText(tbprnom.getCellData(index).toString());
       tfidpatient.setText(tbidpatient.getCellData(index).toString());
        tfnommed.setText(tbnommed.getCellData(index).toString());
        tfidmed.setText(tbidmed.getCellData(index).toString());
        tfdosage.setText(tbdosage.getCellData(index).toString());
        tfprix.setText(tbprix.getCellData(index).toString());
        
     
    }

    @FXML
    private void Edit(ActionEvent event) {
        
         try {
             cnx2 = MyConnection.getInstance().getConnection();
           
            String value =tfid.getText();  
           String value1 =tfcin.getText();
           String value2 =tfnom.getText();
           String value3 =tfprnom.getText();
           String value4 =tfidpatient.getText();
           String value5 = tfnommed.getText();
           String value6 =tfidmed.getText();
           String value7 =tfdosage.getText();
           String value8 =tfprix.getText();
             String requete = "UPDATE facteur SET cin='" + value1 + "', nom='" + value2 + "', prenom='" + value3 + "', id_patient='" + value4 + "', id_medicament='" + value6 + "', nom_med='" + value5 + "', dosage='" + value7 + "', prix='" + value8 + "' WHERE id='" + value + "'";
            System.out.println(" facteur ajoutée avec succes");
             PreparedStatement ps = cnx2.prepareStatement(requete);
 ps.executeUpdate();
  Update();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void delet(ActionEvent event) {
        
        System.out.println("Supprimer Ordonnance Click");
        FacteurGrud sv = new FacteurGrud();
        
        Facteur o = (Facteur) tbfactur.getSelectionModel().getSelectedItem();
        if(o == null)
        {
            this.notifierError("Veuillez Sélectionner une ordonnance avant");
        }
        else
        {
            if(this.notifierConfirmation("Voulez vous Supprimer l'Ordonnance : "+o.getId()))
            {
                sv.supprimerfacteur(o.getId());
                this.notifier("Suppression");
                 Update();
            }
        }
    }

    @FXML
  private void btnPdf(ActionEvent evt) throws FileNotFoundException, DocumentException, IOException
    {
        System.out.println("Boutton PDF Click");
        FacteurGrud sv = new FacteurGrud();
        ServicePDF pdf = new ServicePDF();
        List<Facteur> listeFacteur = sv.afficherfacteurs();
        pdf.genererPdfFacteur("Facteur",listeFacteur );
    }

    @FXML
    private void btnxlsxx(ActionEvent event) throws DocumentException, IOException  {
          System.out.println("Boutton Excel Click");
		FacteurGrud sv = new FacteurGrud();
		ServiceExcel svExcel = new ServiceExcel();
		List<Facteur> listeConsultation = sv.afficherfacteurs();
		svExcel.genererxlsxFacteur("facteur", listeConsultation);
		
			System.out.println("Operation Excel Export");
    }

    @FXML
      private void btnnext(ActionEvent event) throws IOException {
          
         
         Parent mainPageParent = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }
    
}
