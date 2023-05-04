/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Depot;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.DepotCRUD;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ModifierDepotFXMLController implements Initializable {

    @FXML
    private DatePicker dpDate;

    @FXML
    private ComboBox<String> cbRegime;

    @FXML
    private TextField tfEtat;

    @FXML
    private Button enregistrer;

    private Depot depot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    private ObservableList<String> regimes = FXCollections.observableArrayList("APCI", "MO");

    @FXML
    private void validerButton(ActionEvent event) {
        LocalDate now = LocalDate.now();
        LocalDate date = dpDate.getValue();
        if (date.isBefore(now)) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Échec de l'ajout du depot");
            alertError.setContentText("La date saisie est antérieure à la date actuelle.");
            alertError.showAndWait();
            return;
        }
        Depot d = depot;
        // Ajouter les options à la ComboBox
        cbRegime.setItems(regimes);
        depot.setDateDepot(dpDate.getValue());
        depot.setRegime(cbRegime.getValue());
        depot.setEtat(tfEtat.getText());

        DepotCRUD dc = new DepotCRUD();
        boolean resultat = dc.modifier(depot);
        if (resultat) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Dépôt: "+d.getIdDepot()+" modifié avec succès");
            alert.showAndWait();
        }
        Stage stage = (Stage) enregistrer.getScene().getWindow();
        stage.close();
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
        // Ajouter les options à la ComboBox avant de définir la valeur sélectionnée
        cbRegime.setItems(regimes);
        cbRegime.setValue(depot.getRegime());
        dpDate.setValue(depot.getDateDepot());
        tfEtat.setText(depot.getEtat());
    }
}
