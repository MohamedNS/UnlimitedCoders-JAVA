/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import Entity.Medicament;
import Services.ServiceMedicament;
import Services.ServicePDF;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class HomeMedicamentController implements Initializable{

    //Bouttons
    @FXML
    public Button btnRetour;
    @FXML
    public Button btnAjouter;
    @FXML
    public Button btnModifier;
    @FXML
    public Button btnSupprimer;
    @FXML
    public Button btnPdf;

    //Interface Ajouter
    @FXML
    public TextField nomText;
    @FXML
    public TextField dosageText;
    @FXML
    public TextField prixText;
    @FXML
    public TextArea descriptionText;

    //Interface Liste
    @FXML
    private TableView<Medicament> medicamentTable;
    @FXML
    private TableColumn<?,?> idColonne;
    @FXML
    private TableColumn<?,?> nomColonne;
    @FXML
    private TableColumn<?,?> dosageColonne;
    @FXML
    private TableColumn<?,?> prixColonne;
    @FXML
    private TableColumn<?,?> descriptionColonne;

    public HomeMedicamentController()
    {
    }
    public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Medicament");
        alert.setHeaderText(null);
        alert.setContentText("Operation "+operation+" Effectuee avec success");
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

    @FXML
    public void btnRetour(ActionEvent evt)
    {
        System.out.println("Retour Click");
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        stage.close();
        MenuController mc = new MenuController();
        mc.show();
    }
    @FXML
    public void btnAjouter(ActionEvent evt)
    {
        System.out.println("Ajout Medicament Click");
        ServiceMedicament sv = new ServiceMedicament();
        String nom = nomText.getText();
        if(dosageText.getText().isEmpty() && prixText.getText().isEmpty())
        {
            this.notifierError("Operation Ajout Refusee");
        }
        else
        {
            Integer dosage = Integer.parseInt(dosageText.getText());
            Float prix = Float.parseFloat(prixText.getText());
            String description = descriptionText.getText();
            if(this.validerNom(nom) && this.validerDosage(dosage) && this.validerPrix(prix) && this.validerDescription(description))
            {
                Medicament m = new Medicament();
                m.setNom(nom);
                m.setDosage(dosage);
                m.setPrix(prix);
                m.setDescription(description);
                sv.ajouterMedicament(m);
                this.notifier("Ajout");
                afficherListeMedicament();
                this.viderChamps();
            }
        }
    }
    @FXML
    public void btnModifier(ActionEvent evt)
    {
        System.out.println("Modifier Medicament Click");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifierMedicament.fxml"));
        try{
            loader.load();
            Medicament m = (Medicament)medicamentTable.getSelectionModel().getSelectedItem();
            ModifierMedicamentController mc = loader.getController();
            mc.setTextFiels(m);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = (Stage)btnRetour.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    public void btnSupprimer(ActionEvent evt)
    {
        System.out.println("Supprimer Medicament Click");
        ServiceMedicament sv = new ServiceMedicament();
        Medicament m = (Medicament) medicamentTable.getSelectionModel().getSelectedItem();
        sv.supprimerMedicament(m.getId());
        this.notifier("Suppression");
        afficherListeMedicament();
    }
    @FXML
    private void btnPdf(ActionEvent evt) throws FileNotFoundException, DocumentException
    {
        System.out.println("Boutton PDF Click");
        ServiceMedicament sv = new ServiceMedicament();
        ServicePDF pdf = new ServicePDF();
        List<Medicament> listeMedicaments = sv.afficherMedicament();
        pdf.genererPdfMedicament("Medicament", listeMedicaments);
    }

    public ObservableList<Medicament> getMedicaments()
    {
        ObservableList<Medicament> observableListMedicament = FXCollections.observableArrayList();
        ServiceMedicament sv = new ServiceMedicament();
        List<Medicament> listeMedicaments = sv.afficherMedicament();
        for(Medicament medicament:listeMedicaments)
        {
            observableListMedicament.add(medicament);
        }
        return observableListMedicament;
    }

    public void afficherListeMedicament()
    {
        ObservableList<Medicament> list = getMedicaments();
        idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColonne.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dosageColonne.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        prixColonne.setCellValueFactory(new PropertyValueFactory<>("prix"));
        descriptionColonne.setCellValueFactory(new PropertyValueFactory<>("description"));
        medicamentTable.setItems(list);
    }
    public void show()
    {
        try {
            System.out.println("Boutton Retour Click");
            Parent loader = FXMLLoader.load(getClass().getResource("HomeMedicament.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean validerNom(String nom)
    {
        if (nom.isEmpty())
        {
            this.notifierError("'Nom Medicament' est un champ obligatoire");
            return false;
        }
        return true;
    }
    public boolean validerDosage(int dosage)
    {
        if(dosage < 0)
        {
            this.notifierError("Le champ 'Dosage' doit etre un entier positif");
            return false;
        }
        return true;
    }
    public boolean validerPrix(float prix)
    {
        if(prix < 0)
        {
            this.notifierError("Le champ 'Prix' doit etre un entier positif");
            return false;
        }
        return true;
    }
    public boolean validerDescription(String description)
    {
        if(description.isEmpty())
        {
            this.notifierError("Le champ 'Description' est un champ obligatoire");
            return false;
        }
        return true;
    }
    public void viderChamps()
    {
        nomText.setText("");
        dosageText.setText("");
        prixText.setText("");
        descriptionText.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherListeMedicament();
    }
    
}
