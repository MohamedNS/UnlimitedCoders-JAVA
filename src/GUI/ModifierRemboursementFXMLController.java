/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Remboursement;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.RemboursementCRUD;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ModifierRemboursementFXMLController implements Initializable {

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfReponse;

    @FXML
    private Button enregistrer;

    private Remboursement remboursement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void validerButton(ActionEvent event) {
        LocalDate now = LocalDate.now();
        LocalDate date = dpDate.getValue();
        if (date.isBefore(now)) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Échec de l'ajout du remboursement");
            alertError.setContentText("La date saisie est antérieure à la date actuelle.");
            alertError.showAndWait();
            return;
        }
        Remboursement r = remboursement;
        r.setDateRemboursement(dpDate.getValue());
        r.setReponse(tfReponse.getText());

        RemboursementCRUD rc = new RemboursementCRUD();
        boolean resultat = rc.modifier(r);
        if (resultat) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Remboursement: "+r.getIdRemboursement()+ " modifié avec succès");
            alert.showAndWait();
        }
        Stage stage = (Stage) enregistrer.getScene().getWindow();
        stage.close();
    }

    public void setRemboursement(Remboursement remboursement) {
        this.remboursement = remboursement;
        dpDate.setValue(remboursement.getDateRemboursement());
        tfReponse.setText(remboursement.getReponse());
    }

}
