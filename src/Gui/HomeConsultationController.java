/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;
import Entity.Consultation;
import Services.ServiceConsultation;
import Services.ServiceExcel;
import Services.ServicePDF;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author bytesudoer
 */
public class HomeConsultationController implements Initializable{

    public static final  List<String> listeCriteres  = Arrays.asList("Identifiant","Matricule Medecin","Id Patient","Date Consultation","Montant");
    public static final List<String> listeOrdre = Arrays.asList("Croissant","Decroissant");
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnRetour;
    @FXML
    private Button bntPdf;
	@FXML
	private Button btnExcel;
    @FXML
    private Button btnTrier;

    @FXML
    private TextField matriculeMedecinText;
    @FXML
    private TextField idPatientText;
    @FXML
    private DatePicker dateConsultationText;
    @FXML
    private TextField montantText;

    @FXML
    private TableView<Consultation> consultationTable;
    
    @FXML
    private TableColumn<?,?> idColonne;
    @FXML
    private TableColumn<?,?> matriculeColonne;
    @FXML
    private TableColumn<?,?> idPatientColonne;
    @FXML
    private TableColumn<?,?> dateConsultationColonne;
    @FXML
    private TableColumn<?,?> montantColonne;
    
    @FXML
    private ChoiceBox critereChoice;
    @FXML
    private ChoiceBox ordreChoice;


    public HomeConsultationController()
    {
    }

    public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultation");
        alert.setHeaderText(null);
        alert.setContentText("Operation "+operation+" Effectue avec success");
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
    public boolean notifierConfirmation(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,msg,ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	public File selectedFile()
	{
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Choisissez un Fichier à importer");
		return filechooser.showOpenDialog(btnAjouter.getScene().getWindow());
	}

	public boolean notifierOperationExcel()
	{
		ButtonType  importExcel = new ButtonType("Importer");
		ButtonType exportExcel = new ButtonType("Exporter");
		
		Alert alert = new Alert(Alert.AlertType.NONE,"Choisissez une option",importExcel,exportExcel);
		alert.setTitle("Menu Excel");
		alert.showAndWait();
		if(alert.getResult() == importExcel)
		{
			return true;
		}
		else if(alert.getResult() == exportExcel)
		{
			return false;
		}
		return false;
	}

    @FXML
    public void btnAjouter(ActionEvent evt)
    {

        System.out.println("Ajout Consultation Click");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        ServiceConsultation sc = new ServiceConsultation();
        if (matriculeMedecinText.getText().isEmpty() || idPatientText.getText().isEmpty() ||
                 montantText.getText().isEmpty() || dateConsultationText.getValue() == null)
        {
            this.notifierError("Operation Ajout Refusee.Remplissez Tous les champs");
        }
        else
        {
            String matricule = matriculeMedecinText.getText();
            String idPatient = idPatientText.getText();
            Float montant = Float.parseFloat(montantText.getText());
            LocalDate dateConsultation = dateConsultationText.getValue();

            if(this.validerMatriculeMedecin(matricule) && this.validerIdPatient(idPatient) && this.validierMontant(montant))
            {
                Consultation c = new Consultation();
                c.setMatriculeMedecin(matricule);
                c.setIdPatient(idPatient);
                c.setMontant(montant);
                c.setDateConsultation(java.sql.Date.valueOf(dateConsultation));
                sc.ajouterConsultation(c);
                this.notifier("Ajout");
                afficherListeConsultation();
                this.viderChamps();
            }
        }
    }
    @FXML
    public void btnSupprimer(ActionEvent evt)
    {
        System.out.println("Supprimer Consultation click");
        ServiceConsultation sv = new ServiceConsultation();
        
        Consultation c = (Consultation) consultationTable.getSelectionModel().getSelectedItem();
        if(c == null)
        {
            this.notifierError("Veuillez sélectionner une consultation avant");
        }
        else
        {
            if(this.notifierConfirmation("Voulez Vous Supprimer La consultation : "+c.getId()))
            {
                sv.supprimerConsultation(c.getId());
                afficherListeConsultation();
                this.notifier("Suppression");
            }
        }
    }
    @FXML
    public void btnModifier(ActionEvent evt)
    {
        System.out.println("Modifier Consultation Click");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifierConsultation.fxml"));
        try{
            loader.load();
            Consultation c = (Consultation)consultationTable.getSelectionModel().getSelectedItem();
            if(c == null)
            {
                this.notifierError("Veuillez sélectionner une consultation avant");
            }
            else
            {
                if(this.notifierConfirmation("Voulez Vous modifier La Consultation : "+c.getId()))
                {
                    ModifierConsultationController mc = loader.getController();
                    mc.setTextFields(c);
                    Parent root = loader.getRoot();
                    Scene scene = new Scene(root);

                    Stage stage = (Stage)btnRetour.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
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
    public void btnPdf(ActionEvent evt) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
        System.out.println("Boutton PDF Click");
        Consultation c = (Consultation) consultationTable.getSelectionModel().getSelectedItem();
        ServiceConsultation sv = new ServiceConsultation();
        List<Consultation>listeConsultations = sv.afficherConsultation();
        ServicePDF pdf = new ServicePDF();
	    pdf.genererPdfConsultation("Consultation", listeConsultations);
        this.notifier("Generation PDF");
        
    }
	@FXML
	private void btnExcel(ActionEvent evt) throws FileNotFoundException
	{
		System.out.println("Boutton Excel Click");
		ServiceConsultation sv = new ServiceConsultation();
		ServiceExcel svExcel = new ServiceExcel();
		List<Consultation> listeConsultation = sv.afficherConsultation();
		svExcel.generateExcelConsultation(listeConsultation, "Consultation");
		if(this.notifierOperationExcel())
		{
			System.out.println("Operation Excel Import");
			File file = this.selectedFile();
			System.out.println("Selceted File : "+file);
			List<Consultation> listeImport = svExcel.importerExcelConsultation(file);
			for(Consultation c:listeImport)
			{
				sv.ajouterConsultation(c);
			}
			this.notifier("Import Excel");
			this.afficherListeConsultation();
		}
		else
		{
			System.out.println("Operation Excel Export");
		}
	}
    @FXML
    public void btnTrier(ActionEvent evt)
    {
        String critere = (String) critereChoice.getValue();
        String ordre = (String) ordreChoice.getValue();
        if(critere == null || ordre == null)
        {
            this.notifierError("Remplissez les Champs 'Critre' et 'Ordre' avant");
        }
        else
        {
            ServiceConsultation sv = new ServiceConsultation();
            System.out.println("Boutton Trie Click");
            List<Consultation> listeConsultations = sv.trierConsultations(critere, ordre);
            ObservableList<Consultation> list = FXCollections.observableArrayList(listeConsultations);
            idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
            matriculeColonne.setCellValueFactory(new PropertyValueFactory<>("matriculeMedecin"));
            idPatientColonne.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
            dateConsultationColonne.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
            montantColonne.setCellValueFactory(new PropertyValueFactory<>("montant"));
            consultationTable.setItems(list);
        }
    }

    public ObservableList<Consultation> getConsultations()
    {
        ObservableList<Consultation> observableListConsultation = FXCollections.observableArrayList();
        ServiceConsultation sv = new ServiceConsultation();
        List<Consultation> listeConsultation = sv.afficherConsultation();

        for (Consultation consultation : listeConsultation) {
            observableListConsultation.add(consultation);
        }
        return observableListConsultation;
    }

    public void afficherListeConsultation()
    {
        ObservableList<Consultation> list = getConsultations();
        //System.out.println(list);
        idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColonne.setCellValueFactory(new PropertyValueFactory<>("matriculeMedecin"));
        idPatientColonne.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
        dateConsultationColonne.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        montantColonne.setCellValueFactory(new PropertyValueFactory<>("montant"));

        consultationTable.setItems(list);
    }
    public void show()
    {
        try
        {
            System.out.println("Boutton Retour click");
            Parent loader = FXMLLoader.load(getClass().getResource("HomeConsultation.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    // Fonctions Controle Saisie
    public boolean validerMatriculeMedecin(String matricule)
    {
        if(matricule.isEmpty() && matricule.length() < 5)
        {
            this.notifierError("Le champ 'Matricule Medecin' doit contenir au moins 5 caracteres");
            return false;
        }
        return true;
    }
    public boolean validerIdPatient(String idPatient)
    {
        if(idPatient.isEmpty() && idPatient.length() < 5)
        {
            this.notifierError("Le champ 'Identifiant Patient' doit contenir au moins 5 caracteres");
            return false;
        }
        return true;
    }
    public boolean validierMontant(float montant)
    {
        if(montant< 0)
        {
            this.notifierError("Le champ 'Montant' doit etre un entier positif");
            return false;
        }
        return true;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remplireChoiceBoxTri();
        afficherListeConsultation();
    }
    
    public void viderChamps()
    {
        System.out.println("Ajout Effectue. On vide les champs");
        matriculeMedecinText.setText("");
        idPatientText.setText("");
        dateConsultationText.setValue(null);
        montantText.setText("");
    }

    public void remplireChoiceBoxTri()
    {
        ObservableList<String> observableListCritere = FXCollections.observableArrayList();
        ObservableList<String> observableListOrdre = FXCollections.observableArrayList();
        for(String item:listeCriteres)
        {
            observableListCritere.add(item);
        }
        for(String item: listeOrdre)
        {
            observableListOrdre.add(item);
        }
        critereChoice.setItems(observableListCritere);
        ordreChoice.setItems(observableListOrdre);
    }

    
}
