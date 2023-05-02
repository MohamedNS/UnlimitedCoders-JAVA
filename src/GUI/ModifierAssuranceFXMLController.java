/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Assurance;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.AssuranceCRUD;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ModifierAssuranceFXMLController implements Initializable {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPlafond;

    @FXML
    private TextField tfAdresse;

    @FXML
    private Button enregistrer;

    private Assurance assurance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void validerButton(ActionEvent event) {
        Assurance a = assurance;
        a.setNomAssurance(tfNom.getText());
        a.setPlafond(Float.parseFloat(tfPlafond.getText()));
        a.setAdresseAssurance(tfAdresse.getText());

        AssuranceCRUD ac = new AssuranceCRUD();
        boolean resultat = ac.modifier(a);
        if (resultat) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Assurance: "+a.getIdAssurance()+" modifiée avec succès");
            alert.showAndWait();
        }
        Stage stage = (Stage) enregistrer.getScene().getWindow();
        stage.close();
    }

    public void setAssurance(Assurance assurance) {
        this.assurance = assurance;
        tfNom.setText(assurance.getNomAssurance());
        tfPlafond.setText(Float.toString(assurance.getPlafond()));
        tfAdresse.setText(assurance.getAdresseAssurance());
    }

}
