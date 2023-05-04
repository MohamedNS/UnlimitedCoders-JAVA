/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Entity.OrdonnanceN;
import Services.ServiceOrdonnance;
import Services.ServiceOrdonnanceN;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class ModifierOrdonnanceController implements Initializable{

    @FXML
    private Button btnRetour;
    @FXML
    private Button btnModifier;
    @FXML
    private Label labelId;
    @FXML
    private Label labelConsultationId;
    @FXML
    private TextField validiteText;
    @FXML
    private Label labelMedicament;
    

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
        Stage stage = (Stage)btnRetour.getScene().getWindow();
        stage.close();
        HomeOrdonnanceController hc = new HomeOrdonnanceController();
        hc.show();
    }
    @FXML
    private void btnModifier(ActionEvent evt)
    {
        ServiceOrdonnanceN sv = new ServiceOrdonnanceN();
        Integer id = Integer.parseInt(labelId.getText());
        Integer consultationId = Integer.parseInt(labelConsultationId.getText());
        Integer validite = Integer.parseInt(validiteText.getText());
        OrdonnanceN o = new OrdonnanceN(id,consultationId,validite);
        sv.modifierOrdonnance(o);
        this.notifier("Modification");
    }

    public void setTextFields(OrdonnanceN o)
    {
        labelId.setText(String.valueOf(o.getId()));
        labelConsultationId.setText(String.valueOf(o.getConsultation_id()));
        validiteText.setText(String.valueOf(o.getValidite()));
        labelMedicament.setText(o.getNomMedicaments());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
