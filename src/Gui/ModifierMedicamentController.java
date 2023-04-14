/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import Entity.Medicament;
import Services.ServiceMedicament;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class ModifierMedicamentController implements Initializable{

    @FXML
    private Button btnRetour;
    @FXML
    private Button btnModifier;

    @FXML
    private Label labelId;
    @FXML
    private TextField nomText;
    @FXML
    private TextField dosageText;
    @FXML
    private TextField prixText;
    @FXML
    private TextArea descriptionText;

    public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultation");
        alert.setHeaderText(null);
        alert.setContentText("Operation "+operation+" Effectue avec success");
        alert.show();
    }
    @FXML
    public void btnRetour(ActionEvent evt)
    {
        System.out.println("Boutton Retour click");
        Stage stage = (Stage)btnRetour.getScene().getWindow();
        stage.close();
        HomeMedicamentController hm = new HomeMedicamentController();
        hm.show();
    }
    @FXML
    public void btnModifier(ActionEvent evt)
    {
        ServiceMedicament sv = new ServiceMedicament();
        Integer id = Integer.parseInt(labelId.getText());
        String nom = nomText.getText();
        Integer dosage = Integer.parseInt(dosageText.getText());
        Float prix = Float.parseFloat(prixText.getText());
        String description = descriptionText.getText();
        Medicament m = new Medicament(id,nom,dosage,prix,description);
        sv.modifierMedicament(m);
        this.notifier("Modification");
    }

    public void setText(Medicament m)
    {
        labelId.setText(String.valueOf(m.getId()));
        nomText.setText(m.getNom());
        dosageText.setText(String.valueOf(m.getDosage()));
        prixText.setText(String.valueOf(m.getPrix()));
        descriptionText.setText(m.getDescription());
    }
    public void setTextFiels(Medicament m)
    {
        labelId.setText(String.valueOf(m.getId()));
        nomText.setText(m.getNom());
        dosageText.setText(String.valueOf(m.getDosage()));
        prixText.setText(String.valueOf(m.getPrix()));
        descriptionText.setText(m.getDescription());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
