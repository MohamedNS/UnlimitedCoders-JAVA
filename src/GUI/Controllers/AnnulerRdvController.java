/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import Entity.RendezVous;
import Entity.Utilisateur;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Callback;
import Services.RendezVousCrud;
import Services.UtilisateurCrud;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class AnnulerRdvController implements Initializable {

    @FXML
    private TableView<RendezVous> rdvsID;
    @FXML
    private TableColumn<RendezVous, Integer> rdvID;
    @FXML
    private TableColumn<RendezVous, LocalDate> dateColumn;
    @FXML
    private TableColumn<RendezVous, String> medecinID;
    @FXML
    private TableColumn<RendezVous, Void> deleteID;
    @FXML
    private Button backID;
    @FXML
    private AnchorPane container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            rdvID.setCellValueFactory(new PropertyValueFactory<>("id"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            // Populate TableView with data from database
            UtilisateurCrud utilisateurCrud = new UtilisateurCrud();
            Utilisateur patient = utilisateurCrud.getPatientById(8);
            RendezVousCrud rendezVousCrud = new RendezVousCrud();
            List<RendezVous> rendezVousList = rendezVousCrud.getRendezVousByUtilisateurId(patient.getId());
            ObservableList<RendezVous> observableList = FXCollections.observableList(rendezVousList);
            rdvsID.setItems(observableList);
            medecinID.setCellValueFactory(
                    (TableColumn.CellDataFeatures<RendezVous, String> param) -> {
                        RendezVous rendezVous = param.getValue();
                        Utilisateur medecin = rendezVous.getMedecin();
                        String nomPrenom = medecin.getNom() + " " + medecin.getPrenom();
                        return new SimpleStringProperty(nomPrenom);
                    }
            );
            // Set up column with delete button for each row
            Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>> cellFactory = new Callback<TableColumn<RendezVous, Void>, TableCell<RendezVous, Void>>() {
                @Override
                public TableCell<RendezVous, Void> call(final TableColumn<RendezVous, Void> param) {
                    final TableCell<RendezVous, Void> cell = new TableCell<RendezVous, Void>() {

                        private final Button deleteButton = new Button("Supprimer");

                        {
                            deleteButton.setOnAction((ActionEvent event) -> {
                                RendezVous rendezVous = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Supprimer un rendez-vous");
                                alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer ce rendez-vous ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    try {
                                        //AudioClip alertSound = new AudioClip(getClass().getResource("/ressources/notfication.mp3").toString());
                                        //alertSound.play();
                                        RendezVousCrud rendezVousCrud = new RendezVousCrud();
                                        rendezVousCrud.supprimerRdv(rendezVous.getId());
                                        rdvsID.getItems().remove(rendezVous);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(AnnulerRdvController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            });

                        }
                        //// updating the graphical representation of the cell.

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(deleteButton);
                            }
                        }
                    };
                    return cell;
                }
            };

            deleteID.setCellFactory(cellFactory);

            rdvsID.setItems(observableList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/MainPage.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);

        //Get the current window and set the scene to the main page scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }
}
