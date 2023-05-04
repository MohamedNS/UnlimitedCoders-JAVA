/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.Assurance;
import Entity.Depot;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Services.AssuranceCRUD;
import Services.DepotCRUD;
import Services.FicheCRUD;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class DepotFXMLController implements Initializable {

    @FXML
    private DatePicker dpDate;

    @FXML
    private ComboBox<String> cbRegime;

    @FXML
    private ComboBox<String> cbAssurance;

    @FXML
    private ComboBox<String> cbFiche;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Depot> tabDepot;

    @FXML
    private TableColumn<Depot, String> colId;

    @FXML
    private TableColumn<Depot, String> colDate;

    @FXML
    private TableColumn<Depot, String> colRegime;

    @FXML
    private TableColumn<Depot, String> colDepense;

    @FXML
    private TableColumn<Depot, String> colEtat;

    @FXML
    private TableColumn<Depot, String> colAssurance;

    @FXML
    private TableColumn<Depot, String> colFiche;

    @FXML
    private TableColumn<Depot, String> colPatient;

    @FXML
    private TableColumn<Depot, Void> modifierCol;

    @FXML
    private Button btnRetour;
    
    @FXML
    private Button exportEx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        final DepotCRUD dc = new DepotCRUD();
        ObservableList<Depot> depots = FXCollections.observableArrayList(dc.afficher());
        ObservableList<String> regimes = FXCollections.observableArrayList("APCI", "MO");
        cbRegime.setItems(regimes);

        AssuranceCRUD ac = new AssuranceCRUD();
        List<String> nomsAssurances = new ArrayList<>();
        List<Integer> idsAssurances = new ArrayList<>(); // Ajout de la liste d'IDs des assurances
        for (Assurance assurance : ac.listerAssurance()) {
            nomsAssurances.add(assurance.getNomAssurance());
            idsAssurances.add(assurance.getIdAssurance()); // Ajout de l'ID de chaque assurance à la liste
        }
        cbAssurance.getItems().addAll(nomsAssurances);

        FicheCRUD fc = new FicheCRUD();
        cbFiche.getItems().addAll(fc.listerIDsFiches());

        tabDepot.setItems(depots);
        colId.setCellValueFactory(new PropertyValueFactory<Depot, String>("idDepot"));
        colDate.setCellValueFactory(new PropertyValueFactory<Depot, String>("dateDepot"));
        colRegime.setCellValueFactory(new PropertyValueFactory<Depot, String>("regime"));
        colDepense.setCellValueFactory(new PropertyValueFactory<Depot, String>("totalDepense"));
        colEtat.setCellValueFactory(new PropertyValueFactory<Depot, String>("etat"));
        colAssurance.setCellValueFactory(new PropertyValueFactory<Depot, String>("idAssurance"));
        colFiche.setCellValueFactory(new PropertyValueFactory<Depot, String>("idFiche"));
        colPatient.setCellValueFactory(new PropertyValueFactory<Depot, String>("idPatient"));

       // Set up filtered list
        final FilteredList<Depot> filteredList = new FilteredList<>(depots, new Predicate<Depot>() {
            @Override
            public boolean test(Depot depot) {
                return true;
            }
        });
        final SortedList<Depot> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tabDepot.comparatorProperty());
        tabDepot.setItems(sortedList);

// Set up search field listener
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                filteredList.setPredicate(new Predicate<Depot>() {
                    @Override
                    public boolean test(Depot depot) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String[] keywords = newValue.toLowerCase().split(" ");
                        boolean matchAll = true;
                        for (String keyword : keywords) {
                            if (keyword.isEmpty()) {
                                continue;
                            }
                            boolean match = false;
                            if (Integer.toString(depot.getIdDepot()).contains(keyword)) {
                                match = true;
                            } else if (Integer.toString(depot.getIdAssurance()).contains(keyword)) {
                                match = true;
                            } else if (Integer.toString(depot.getIdFiche()).contains(keyword)) {
                                match = true;
                            } else if (Integer.toString(depot.getIdPatient()).contains(keyword)) {
                                match = true;
                            } else if (depot.getDateDepot().toString().contains(keyword)) {
                                match = true;
                            } else if (depot.getRegime().toLowerCase().contains(keyword)) {
                                match = true;
                            } else if (Float.toString(depot.getTotalDepense()).contains(keyword)) {
                                match = true;
                            } else if (depot.getEtat().toLowerCase().contains(keyword)) {
                                match = true;
                            }
                            if (!match) {
                                matchAll = false;
                                break;
                            }
                        }
                        return matchAll;
                    }
                });
            }
        });



        modifierCol.setCellFactory(new Callback<TableColumn<Depot, Void>, TableCell<Depot, Void>>() {

            @Override
            public TableCell<Depot, Void> call(final TableColumn<Depot, Void> param) {
                final TableCell<Depot, Void> cell = new TableCell<Depot, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Récupérer la convention correspondante
                                Depot depot = getTableView().getItems().get(getIndex());

                                // Ouvrir la fenêtre de modification pour cette convention
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierDepotFXML.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(DepotFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ModifierDepotFXMLController controller = loader.getController();
                                controller.setDepot(depot);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.showAndWait();

                                // Rafraîchir la table des conventions
                                tabDepot.setItems(FXCollections.observableArrayList(dc.afficher()));
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    void ajouter(ActionEvent event) {
        DepotCRUD dc = new DepotCRUD();

        AssuranceCRUD ac = new AssuranceCRUD();
        List<String> nomsAssurances = new ArrayList<>();
        List<Integer> idsAssurances = new ArrayList<>(); // Ajout de la liste d'IDs des assurances
        for (Assurance assurance : ac.listerAssurance()) {
            nomsAssurances.add(assurance.getNomAssurance());
            idsAssurances.add(assurance.getIdAssurance()); // Ajout de l'ID de chaque assurance à la liste
        }
        Depot d = null;
        LocalDate date = dpDate.getValue();
        String regime = cbRegime.getValue();
        String assurance = cbAssurance.getValue();
        String fiche = cbFiche.getValue();

        if (date == null || regime.isEmpty() || assurance.isEmpty() || fiche.isEmpty()) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Échec de l'ajout du depot");
            alertError.setContentText("Veuillez remplir tous les champs.");
            alertError.showAndWait();
            return;
        }

        try {
            LocalDate now = LocalDate.now();
            if (date.isBefore(now)) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Échec de l'ajout du depot");
                alertError.setContentText("La date saisie est antérieure à la date actuelle.");
                alertError.showAndWait();
                return;
            }
            int assuranceIndex = nomsAssurances.indexOf(assurance); // Récupération de l'index de l'assurance sélectionnée
            int assuranceValue = idsAssurances.get(assuranceIndex); // Récupération de l'ID de l'assurance à partir de l'index
            int ficheValue = Integer.parseInt(fiche);
            FicheCRUD fc = new FicheCRUD();
            int idPatient = fc.getIdPatient(ficheValue);
            d = new Depot(assuranceValue, ficheValue, idPatient, date, "En attente", regime, 0);
            dc.calculerTotalDepense(d);
            float depense = d.getTotalDepense();
            if (depense < 0) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Échec de l'ajout du depot");
                alertError.setContentText("La valeur du total depense ne peut pas être négative.");
                alertError.showAndWait();
                return;
            }
            d = new Depot(assuranceValue, ficheValue, idPatient, date, "En attente", regime, depense);
            dc.ajouter(d);
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Succes");
            alertSuccess.setHeaderText("Ajout du depot réussi");
            alertSuccess.setContentText("Le depot: " + d.getIdDepot() + " a été ajouté à la base de données.");
            alertSuccess.showAndWait();
            this.viderChamps();
        } catch (NumberFormatException e) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Échec de l'ajout du depot");
            alertError.setContentText("Veuillez saisir une valeur numérique valide pour le total depense, l'assurance,la fiche et le patient.");
            alertError.showAndWait();
        }

    }

    @FXML
    void afficher(ActionEvent event) {
        DepotCRUD dc = new DepotCRUD();
        ObservableList<Depot> depots = FXCollections.observableArrayList(dc.afficher());
        tabDepot.setItems(depots);
        colId.setCellValueFactory(new PropertyValueFactory<Depot, String>("idDepot"));
        colDate.setCellValueFactory(new PropertyValueFactory<Depot, String>("dateDepot"));
        colRegime.setCellValueFactory(new PropertyValueFactory<Depot, String>("regime"));
        colDepense.setCellValueFactory(new PropertyValueFactory<Depot, String>("totalDepense"));
        colEtat.setCellValueFactory(new PropertyValueFactory<Depot, String>("etat"));
        colAssurance.setCellValueFactory(new PropertyValueFactory<Depot, String>("idAssurance"));
        colFiche.setCellValueFactory(new PropertyValueFactory<Depot, String>("idFiche"));
        colPatient.setCellValueFactory(new PropertyValueFactory<Depot, String>("idPatient"));
    }

    @FXML
    private void supprimer(ActionEvent event) {

        Depot selectedDepot = tabDepot.getSelectionModel().getSelectedItem();
        if (selectedDepot != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer un depot");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce depot: " + selectedDepot.getIdDepot() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DepotCRUD dc = new DepotCRUD();
                boolean deleted = dc.supprimer(selectedDepot);
                if (deleted) {

                    tabDepot.getItems().remove(selectedDepot);

                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Suppression échouée");
                    errorAlert.setContentText("Une erreur est survenue lors de la suppression du depot.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucun depot sélectionné");
            alert.setContentText("Veuillez sélectionner un depot à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void verifier(ActionEvent event) {
        Depot selectedDepot = tabDepot.getSelectionModel().getSelectedItem();
        if (selectedDepot != null) {
            LocalDate dateDepot = selectedDepot.getDateDepot();
            FicheCRUD fc = new FicheCRUD();
            LocalDate dateFiche = fc.getDateFiche(selectedDepot.getIdFiche());
            long daysBetween = ChronoUnit.DAYS.between(dateFiche, dateDepot);
            if (daysBetween > 60) {
                selectedDepot.setEtat("Refuse");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText("Période trop longue");
                alert.setContentText("La période entre la date du dépôt et la date de la fiche dépasse 60 jours.");
                alert.showAndWait();
            } else {
                selectedDepot.setEtat("Approuve");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Période acceptable");
                alert.setContentText("La période entre la date du dépôt et la date de la fiche est inférieure ou égale à 60 jours.");
                alert.showAndWait();
            }
            DepotCRUD dc = new DepotCRUD();
            boolean updated = dc.modifier(selectedDepot);
            if (updated) {
                refreshTableDepot();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Mise à jour échouée");
                errorAlert.setContentText("Une erreur est survenue lors de la mise à jour de l'état du dépôt.");
                errorAlert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucun dépôt sélectionné");
            alert.setContentText("Veuillez sélectionner un dépôt.");
            alert.showAndWait();
        }
    }

    @FXML
    public void refreshTableDepot() {
        // This is an example implementation. Replace it with your own code.
        System.out.println("Refreshing table...");
    }

    @FXML
    public void btnRetour(ActionEvent evt) {
        System.out.println("Retour Click");
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        stage.close();
        MenuFXMLController mc = new MenuFXMLController();
        mc.show();
    }

    public void viderChamps() {
        System.out.println("Ajout Effectue. On vide les champs");
        dpDate.setValue(null);
        cbRegime.setValue(null);
        cbAssurance.setValue(null);
        cbFiche.setValue(null);
    }
    
    @FXML
    private void exporterEx(ActionEvent event) {
    DepotCRUD dc = new DepotCRUD();
    ObservableList<Depot> depots = FXCollections.observableArrayList(dc.afficher());
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("Details Depot");
    XSSFRow header = sheet.createRow(0);
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Date");
    header.createCell(2).setCellValue("Regime");
    header.createCell(3).setCellValue("Total Depenses");
    header.createCell(4).setCellValue("Etat");
    header.createCell(5).setCellValue("ID Assurance");
    header.createCell(6).setCellValue("ID Fiche");
    header.createCell(7).setCellValue("ID Patient");
    
    int rowNum = 1;
    for (Depot depot : depots) {
        XSSFRow row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(depot.getIdDepot());
       row.createCell(1).setCellValue(Date.from(depot.getDateDepot().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        row.createCell(2).setCellValue(depot.getRegime());
        row.createCell(3).setCellValue(depot.getTotalDepense());
        row.createCell(4).setCellValue(depot.getEtat());
        row.createCell(5).setCellValue(depot.getIdAssurance());
        row.createCell(6).setCellValue(depot.getIdFiche());
        row.createCell(7).setCellValue(depot.getIdPatient());
    }

    try {
        FileOutputStream fileOut = new FileOutputStream("Depots.xlsx");
        wb.write(fileOut);
        fileOut.close();
        //wb.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Excel");
        alert.setHeaderText("Exportation terminée");
        alert.setContentText("La table depot a été exportée avec succès en une table Excel.");
        alert.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
