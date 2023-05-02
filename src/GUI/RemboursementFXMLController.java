/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Assurance;
import entities.Depot;
import entities.Remboursement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import services.AssuranceCRUD;
import services.DepotCRUD;
import services.EmailService;
import services.RemboursementCRUD;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class RemboursementFXMLController implements Initializable {

    @FXML
    private DatePicker dpDate;

    @FXML
    private ComboBox<String> cbDepot;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Remboursement> tabRemboursement;

    @FXML
    private TableColumn<Remboursement, String> colId;

    @FXML
    private TableColumn<Remboursement, String> colDate;

    @FXML
    private TableColumn<Remboursement, String> colReponse;

    @FXML
    private TableColumn<Remboursement, String> colMontant;

    @FXML
    private TableColumn<Remboursement, String> colDepot;

    @FXML
    private TableColumn<Remboursement, Void> modifierCol;

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
        final RemboursementCRUD rc = new RemboursementCRUD();
        ObservableList<Remboursement> remboursements = FXCollections.observableArrayList(rc.afficher());
        DepotCRUD dc = new DepotCRUD();
        cbDepot.getItems().addAll(dc.listerIDsDepots());
        tabRemboursement.setItems(remboursements);
        colId.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("idRemboursement"));
        colDate.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("dateRemboursement"));
        colReponse.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("reponse"));
        colMontant.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("montantRembourse"));
        colDepot.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("idDepot"));

        // Set up filtered list
        final FilteredList<Remboursement> filteredList = new FilteredList<>(remboursements, new Predicate<Remboursement>() {
            @Override
            public boolean test(Remboursement remboursement) {
                return true;
            }
        });
        final SortedList<Remboursement> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tabRemboursement.comparatorProperty());
        tabRemboursement.setItems(sortedList);

// Set up search field listener
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                filteredList.setPredicate(new Predicate<Remboursement>() {
                    @Override
                    public boolean test(Remboursement remboursement) {
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
                            if (Integer.toString(remboursement.getIdDepot()).contains(keyword)) {
                                match = true;
                            } else if (Integer.toString(remboursement.getIdRemboursement()).contains(keyword)) {
                                match = true;
                            } else if (remboursement.getDateRemboursement().toString().contains(keyword)) {
                                match = true;
                            } else if (Float.toString(remboursement.getMontantRembourse()).contains(keyword)) {
                                match = true;
                            } else if (remboursement.getReponse().toLowerCase().contains(keyword)) {
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


        modifierCol.setCellFactory(new Callback<TableColumn<Remboursement, Void>, TableCell<Remboursement, Void>>() {

            @Override
            public TableCell<Remboursement, Void> call(final TableColumn<Remboursement, Void> param) {
                final TableCell<Remboursement, Void> cell = new TableCell<Remboursement, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Récupérer la convention correspondante
                                Remboursement remboursement = getTableView().getItems().get(getIndex());

                                // Ouvrir la fenêtre de modification pour cette convention
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierRemboursementFXML.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(RemboursementFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                ModifierRemboursementFXMLController controller = loader.getController();
                                controller.setRemboursement(remboursement);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.showAndWait();

                                // Rafraîchir la table des conventions
                                tabRemboursement.setItems(FXCollections.observableArrayList(rc.afficher()));
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
        RemboursementCRUD rc = new RemboursementCRUD();
        Remboursement r = null;
        LocalDate date = dpDate.getValue();
        String depot = cbDepot.getValue();

        if (date == null || depot.isEmpty()) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Échec de l'ajout du remboursement");
            alertError.setContentText("Veuillez remplir tous les champs.");
            alertError.showAndWait();
            return;
        }

        try {
            LocalDate now = LocalDate.now();
            if (date.isBefore(now)) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Échec de l'ajout du remboursement");
                alertError.setContentText("La date saisie est antérieure à la date actuelle.");
                alertError.showAndWait();
                return;
            }
            int depotValue = Integer.parseInt(depot);
            r = new Remboursement(depotValue, "En cours", date, 0);
            float montant = rc.calculerMontantRembourse(r);
            if (montant < 0) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Échec de l'ajout du remboursement");
                alertError.setContentText("La valeur du montant ne peut pas être négative.");
                alertError.showAndWait();
                return;
            }
            r = new Remboursement(depotValue, "En cours", date, montant);
            rc.ajouter(r);
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Succes");
            alertSuccess.setHeaderText("Ajout du remboursement réussi");
            alertSuccess.setContentText("Le remboursement: " + r.getIdRemboursement() + " a été ajouté à la base de données.");
            alertSuccess.showAndWait();
            this.viderChamps();
        } catch (NumberFormatException e) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Erreur");
            alertError.setHeaderText("Échec de l'ajout du remboursement");
            alertError.setContentText("Veuillez saisir une valeur numérique valide pour le montant et le dépôt.");
            alertError.showAndWait();
        }

    }

    @FXML
    void afficher(ActionEvent event) {
        RemboursementCRUD rc = new RemboursementCRUD();
        ObservableList<Remboursement> remboursements = FXCollections.observableArrayList(rc.afficher());
        tabRemboursement.setItems(remboursements);
        colId.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("idRemboursement"));
        colDate.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("dateRemboursement"));
        colReponse.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("reponse"));
        colMontant.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("montantRembourse"));
        colDepot.setCellValueFactory(new PropertyValueFactory<Remboursement, String>("idDepot"));
    }

    @FXML
    private void supprimer(ActionEvent event) {

        Remboursement selectedRemboursement = tabRemboursement.getSelectionModel().getSelectedItem();
        if (selectedRemboursement != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer un remboursement");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce remboursement: " +selectedRemboursement.getIdRemboursement()+" ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                RemboursementCRUD sc = new RemboursementCRUD();
                boolean deleted = sc.supprimer(selectedRemboursement);
                if (deleted) {

                    tabRemboursement.getItems().remove(selectedRemboursement);

                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Suppression échouée");
                    errorAlert.setContentText("Une erreur est survenue lors de la suppression du remboursement.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucun remboursement sélectionnée");
            alert.setContentText("Veuillez sélectionner un remboursement à supprimer.");
            alert.showAndWait();
        }

    }

    @FXML
    private void verifier(ActionEvent event) {
        Remboursement selectedRemboursement = tabRemboursement.getSelectionModel().getSelectedItem();
        if (selectedRemboursement != null) {
            // Récupérer l'ID du dépôt associé
            int idDepot = selectedRemboursement.getIdDepot();
            DepotCRUD dc = new DepotCRUD();
            Depot selectedDepot = dc.getDepotById(idDepot);
            if (selectedDepot != null) {
                int idAssurance = selectedDepot.getIdAssurance();
                AssuranceCRUD ac = new AssuranceCRUD();
                Assurance selectedAssurance = ac.getAssuranceById(idAssurance);
                if (selectedAssurance != null) {
                    double plafond = selectedAssurance.getPlafond();
                    // Récupérer la somme des remboursements pour les dépôts du patient
                    int idPatient = selectedDepot.getIdPatient();
                    RemboursementCRUD rc = new RemboursementCRUD();

                    double sommeRemboursements = rc.getSommeRemboursementsByIdPatient(idPatient);
                    // Comparer avec le plafond
                    if (sommeRemboursements > plafond) {
                        selectedRemboursement.setReponse("Refuse");
                    } else {
                        selectedRemboursement.setReponse("Decompte");
                    }
                    boolean updated = rc.modifier(selectedRemboursement);

                    if (updated) {
                        refreshTableRemboursement();
                        if (selectedRemboursement.getReponse().equals("Decompte")) {
                            EmailService es = new EmailService();
                            String emailPatient = es.getEmailPatientById(selectedDepot.getIdPatient());
                            if (emailPatient != null) {
                                EmailService.sendEmail(emailPatient, selectedRemboursement);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Avertissement");
                                alert.setHeaderText("Email introuvable");
                                alert.setContentText("L'email du patient associé à ce remboursement est introuvable.");
                                alert.showAndWait();
                            }
                        }

                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText("Mise à jour échouée");
                        errorAlert.setContentText("Une erreur est survenue lors de la mise à jour de la réponse du remboursement.");
                        errorAlert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Avertissement");
                    alert.setHeaderText("Assurance introuvable");
                    alert.setContentText("L'assurance associée à ce dépôt est introuvable.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText("Dépôt introuvable");
                alert.setContentText("Le dépôt associé à ce remboursement est introuvable.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucun remboursement sélectionné");
            alert.setContentText("Veuillez sélectionner un remboursement.");
            alert.showAndWait();
        }
    }

    @FXML
    public void refreshTableRemboursement() {
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
        cbDepot.setValue(null);
    }
    
     @FXML
    private void exporterEx(ActionEvent event) {
    RemboursementCRUD rc = new RemboursementCRUD();
    ObservableList<Remboursement> remboursements = FXCollections.observableArrayList(rc.afficher());
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("Details Remboursement");
    XSSFRow header = sheet.createRow(0);
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Date");
    header.createCell(2).setCellValue("Reponse");
    header.createCell(3).setCellValue("Montant Rembourse");
    header.createCell(4).setCellValue("ID Depot");
    
    int rowNum = 1;
    for (Remboursement remboursement : remboursements) {
        XSSFRow row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(remboursement.getIdRemboursement());
       row.createCell(1).setCellValue(Date.from(remboursement.getDateRemboursement().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        row.createCell(2).setCellValue(remboursement.getReponse());
        row.createCell(3).setCellValue(remboursement.getMontantRembourse());
        row.createCell(4).setCellValue(remboursement.getIdDepot());
    }

    try {
        FileOutputStream fileOut = new FileOutputStream("Remboursements.xlsx");
        wb.write(fileOut);
        fileOut.close();
        wb.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Excel");
        alert.setHeaderText("Exportation terminée");
        alert.setContentText("La table remboursement a été exportée avec succès en une table Excel.");
        alert.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
