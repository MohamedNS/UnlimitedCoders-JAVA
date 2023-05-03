/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.FicheAssurance;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import services.FicheAssuranceGrud;
/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterFicheAssuranceController implements Initializable {

    @FXML
    private AnchorPane tfnommed;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfcin;
    @FXML
    private TextField tftotal;
    @FXML
    private Button tfsave;
    @FXML
    private Button next;
    @FXML
    private TextField tfmail;
    @FXML
    private Button mail;
    @FXML
    private Button Btnqr;
    @FXML
    private TextField tfhonoraires;
    @FXML
    private TextField tfmatriculefiscal;
    @FXML
    private TextField tfmatriculecnam;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfdesignation;

    /**
     * Initializes the controller class.
     */
     public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Facur");
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
        FicheAssuranceGrud sc =new FicheAssuranceGrud();
        String cin =tfcin.getText();
        String nom =tfnom.getText();
        String prenom =tfprenom.getText();
        String addresse =tfadresse.getText();
        String matricule_cnam =tfmatriculecnam.getText();
        int matricule_fiscal =Integer.parseInt(tfmatriculefiscal.getText()) ;
        int honoraires =Integer.parseInt(tfhonoraires.getText()) ;
        String designation=tfdesignation.getText();
        LocalDate date = tfdate.getValue();
        int total  =Integer.parseInt (tftotal.getText());
        System.out.println("FicheAssurance est "+""+"\n");
        if(tfcin.getText().isEmpty() ||tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() ||tfadresse.getText().isEmpty() ||tfmatriculecnam.getText().isEmpty() ||tfmatriculefiscal.getText().isEmpty() ||tfhonoraires.getText().isEmpty())
        {
            this.notifierError("Operation Ajout refusee.Remplissez tous les champs");
        }
   
        FicheAssurance c =new FicheAssurance();
        c.setCin(cin);
          c.setNom(nom);
                c.setPrenom(prenom);
                c.setAddresse(addresse);
                c.setMatricule_cnam(matricule_cnam);
                c.setMatricule_fiscal(matricule_fiscal);
                c.setHonoraires(honoraires);
                c.setDate(java.sql.Date.valueOf(date));
                c.setTotal(total);
                sc.ajouterFicheAssurance(c);
        FicheAssuranceGrud pc = new FicheAssuranceGrud();
        pc.ajouterFicheAssurance(c);
        System.out.println("FicheAssurance est "+c+"\n");
    }

    @FXML
    private void btnnext(ActionEvent event) {
        Stage stage = (Stage) next.getScene().getWindow();
       
        stats mc = new stats();
        mc.show();
    }

    @FXML
    private void btnEnvoyer(ActionEvent event) {
    }

    @FXML
    private void gereQRcode(ActionEvent event) {
    }
    
}
