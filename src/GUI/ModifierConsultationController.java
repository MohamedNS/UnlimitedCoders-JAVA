/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Entity.Consultation;
import Services.ServiceConsultationN;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class ModifierConsultationController implements Initializable {

    @FXML
    private Label labelId;
    @FXML
    private Button btnRetour;
    @FXML 
    private Button btnModifer;

    @FXML
    private TextField matriculeText;
    @FXML
    private TextField idPatientText;
    @FXML
    private DatePicker dateConsultationPicker;
    @FXML
    private TextField montantText;

    public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultation");
        alert.setHeaderText(null);
        alert.setContentText("Operation "+operation+" Effectue avec success");
        alert.show();
    }

    @FXML
    private void btnRetour(ActionEvent evt)
    {
        System.out.println("Boutton Retour Click");
        Stage stage =(Stage)btnRetour.getScene().getWindow();
        stage.close();
        HomeConsultationController hc = new HomeConsultationController();
        hc.show();
    }

    @FXML
    public void btnModifier(ActionEvent evt)
    {
        ServiceConsultationN sv = new ServiceConsultationN();
        Integer id = Integer.parseInt(labelId.getText());
        String matriculeMedecin = matriculeText.getText();
        String idPatient = idPatientText.getText();
        LocalDate dateConsultation = dateConsultationPicker.getValue();
        Float montant = Float.parseFloat(montantText.getText());

        Consultation c = new Consultation(id,matriculeMedecin,idPatient,
                java.sql.Date.valueOf(dateConsultation),montant);
        sv.modifierConsultation(c);
        this.notifier("Modification");
        
    }

    public void setText(Consultation c)
    {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        labelId.setText(String.valueOf(c.getId()));
        matriculeText.setText(c.getMatriculeMedecin());
        idPatientText.setText(c.getIdPatient());
        dateConsultationPicker.setValue(LocalDate.parse(formatter.format(c.getDateConsultation())));
        montantText.setText(String.valueOf(c.getMontant()));
    }
    public void setTextFields(Consultation c)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        labelId.setText(String.valueOf(c.getId()));
        matriculeText.setText(c.getMatriculeMedecin());
        idPatientText.setText(c.getIdPatient());
        dateConsultationPicker.setValue(LocalDate.parse(formatter.format(c.getDateConsultation())));
        montantText.setText(String.valueOf(c.getMontant()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
