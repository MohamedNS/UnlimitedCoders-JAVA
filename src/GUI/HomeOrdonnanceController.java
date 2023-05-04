/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;
import Entity.Consultation;
import Entity.Medicament;
import Entity.OrdonnanceN;
import Services.ServiceConsultationN;
import Services.ServiceMedicament;
import Services.ServiceOrdonnance;
import Services.ServicePDF;
import Services.ServiceMail;
import Services.ServiceMailN;
import Services.ServiceOrdonnanceN;
import Services.ServicePDFN;
import Services.ServicesMedicamentN;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class HomeOrdonnanceController implements Initializable{

    public static final List<String> listeCritere = Arrays.asList("Identifiant","Consultation","Validite");
    public static final List<String> listeOrdre = Arrays.asList("Croissant","Decroissant");

    @FXML 
    private Button btnRetour;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnPdf;
    @FXML
    private Button bntTrier;
	@FXML
	private Button btnEnvoyer;

    //interface Afficher
    @FXML
    private TableView<OrdonnanceN> ordonnanceTable;
    @FXML
    private TableColumn<?,?> idColonne;
    @FXML
    private TableColumn<?,?> consultationColonne;
    @FXML
    private TableColumn<?,?> validiteColonne;
    @FXML
    private TableColumn<?,?> medicamentColonne;

    //interface Ajouter
    @FXML
    private ChoiceBox idConsultationText;
    @FXML
    private TextField validiteText;
    @FXML
    private ChoiceBox medicamentText;
    @FXML
    private ChoiceBox critereChoice;
    @FXML
    private ChoiceBox ordreChoice;

	//interface mail
	@FXML
	private TextField toText;
	@FXML
	private TextField subjectText;
	@FXML
	private TextArea textemailText;

    public HomeOrdonnanceController()
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
    @FXML
    public void btnAjouter(ActionEvent evt)
    {
        System.out.println("Ajout Ordonnance Click");
        ServiceOrdonnanceN sv = new ServiceOrdonnanceN();
		ServiceMailN svMail = new ServiceMailN();
		ServiceConsultationN svConsultation = new ServiceConsultationN();
		ServicesMedicamentN svMedicament = new ServicesMedicamentN();
        if(validiteText.getText().isEmpty() || idConsultationText.getSelectionModel().isEmpty() || medicamentText.getSelectionModel().isEmpty())
        {
            this.notifierError("Operation Ajout refusee.Remplissez tous les champs");
        }
        else
        {
			int consultationId = (int) idConsultationText.getSelectionModel().getSelectedItem();
            int validite = Integer.parseInt(validiteText.getText());
            String nomMedicament = (String)medicamentText.getSelectionModel().getSelectedItem();
            if(this.validerIdConsultation(consultationId) && this.validerValidier(validite) && this.validerNomMedicament(nomMedicament))
            {
                OrdonnanceN o = new OrdonnanceN();
                o.setConsultation_id(consultationId);
                o.setValidite(validite);
                sv.ajouterOrdonnance(o);
                this.notifier("Ajout");
                afficherOrdonnance();
                this.viderChamps();
				svMail.notifierValidite(o, svConsultation.trouverParId(o.getConsultation_id()),svMedicament.trouverParNom(
					nomMedicament));
		            }
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
    public void btnSupprimer(ActionEvent evt)
    {
        System.out.println("Supprimer Ordonnance Click");
        ServiceOrdonnanceN sv = new ServiceOrdonnanceN();
        
        OrdonnanceN o = (OrdonnanceN) ordonnanceTable.getSelectionModel().getSelectedItem();
        if(o == null)
        {
            this.notifierError("Veuillez Sélectionner une ordonnance avant");
        }
        else
        {
            if(this.notifierConfirmation("Voulez vous Supprimer l'Ordonnance : "+o.getId()))
            {
                sv.supprimerOrdonnance(o.getId());
                this.notifier("Suppression");
                afficherOrdonnance();
            }
        }
    }
    @FXML
    public void btnModifier(ActionEvent evt)
    {
        System.out.println("Modifer Ordonnance Click");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifierOrdonnance.fxml"));
        try{
            loader.load();
            OrdonnanceN o = (OrdonnanceN) ordonnanceTable.getSelectionModel().getSelectedItem();
            if(o == null)
            {
                this.notifierError("Veuillez sélectionner une ordonnance avant");
            }
            else
            {
                if(this.notifierConfirmation("Voulez vou modifier l'Ordonnance : "+o.getId()))
                {
                    ModifierOrdonnanceController mc = loader.getController();
                    mc.setTextFields(o);
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
    private void btnPdf(ActionEvent evt) throws FileNotFoundException, DocumentException, IOException
    {
        System.out.println("Boutton PDF Click");
        ServiceOrdonnanceN sv = new ServiceOrdonnanceN();
        ServicePDFN pdf = new ServicePDFN();
        List<OrdonnanceN> listeOrdonnances = sv.afficherOrdonnance();
        pdf.genererPdfOrdonnance("Ordonnance", listeOrdonnances);
    }
    @FXML
    public void btnTrier(ActionEvent evt)
    {
        String critere = (String)critereChoice.getValue();
        String ordre = (String)ordreChoice.getValue();
        if(critere == null || ordre == null)
        {
            this.notifierError("Remplissez les Champs 'Critere' et 'Ordre' avant");
        }
        else
        {
            ServiceOrdonnanceN sv = new ServiceOrdonnanceN();
            System.out.println("Boutton Tri click");
            List<OrdonnanceN> listeOrdonnances = sv.trierOrdonnances(critere, ordre);
            ObservableList<OrdonnanceN> list = FXCollections.observableArrayList(listeOrdonnances);
            idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
            consultationColonne.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
            validiteColonne.setCellValueFactory(new PropertyValueFactory<>("validite"));
            medicamentColonne.setCellValueFactory(new PropertyValueFactory<>("nomMedicaments"));
            ordonnanceTable.setItems(list);
        }
    }

	@FXML
	public void btnEnvoyer(ActionEvent evt)
	{
		ServiceMail sv = new ServiceMail();
		System.out.println("Bouttone Mail Click");
		if(toText.getText().isEmpty() || subjectText.getText().isEmpty() || textemailText.getText().isEmpty() )
		{
			this.notifierError("Avant d'envoyer un Mail, Remplissez tous les champs");
		}
		else
		{
			if(this.validerEmail(toText.getText()))
			{
				String adresseTo = toText.getText();
				String subject = subjectText.getText();
				String text = textemailText.getText();
				sv.sendMail(adresseTo, subject, text);
				this.notifier("Message envoyé avec succées vers : "+adresseTo);
			}
			else
			{
				this.notifierError("L'adresse mail : ' "+toText.getText()+"' n'est pas une adresse mail valide");
			}
		}
		

	}
    public void show()
    {
        try{
            System.out.println("Boutton Retour Click");
            Parent loader = FXMLLoader.load(getClass().getResource("HomeOrdonnance.fxml"));
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

    public ObservableList<OrdonnanceN> getOrdonnances()
    {
        ObservableList<OrdonnanceN> observableListOrdonnance = FXCollections.observableArrayList();
        ServiceOrdonnanceN sv = new ServiceOrdonnanceN();
        List<OrdonnanceN> listeOrdonnances = sv.afficherOrdonnance();
        for(OrdonnanceN ordonnance: listeOrdonnances)
        {
            //System.out.println("ordonnance Affichage : "+ordonnance);
            observableListOrdonnance.add(ordonnance);
        }
        return observableListOrdonnance;
    }

    public void afficherOrdonnance()
    {
        ObservableList<OrdonnanceN> list = getOrdonnances();
        idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
        consultationColonne.setCellValueFactory(new PropertyValueFactory<>("consultation_id"));
        validiteColonne.setCellValueFactory(new PropertyValueFactory<>("validite"));
        medicamentColonne.setCellValueFactory(new PropertyValueFactory<>("nomMedicaments"));
        
        ordonnanceTable.setItems(list);
    }
    public void ajouterIdConsultationChoiceBox()
    {
        ObservableList<Integer> observableListIdConsultation = FXCollections.observableArrayList();
        ServiceConsultationN sv = new ServiceConsultationN();
        List<Consultation> listeConsultation = sv.afficherConsultation();
        for(Consultation consultation:listeConsultation)
        {
            //System.out.println("Consultation : "+consultation);
            observableListIdConsultation.add(consultation.getId());
        }
        idConsultationText.setItems(observableListIdConsultation);
    }
    public void ajouterNomMedicamentsChoiceBox()
    {
        ObservableList<String> observableListNomMedicaments = FXCollections.observableArrayList();
        ServiceMedicament sv = new ServiceMedicament();
        List<Medicament> listeMedicaments = sv.afficherMedicament();
        for(Medicament medicament: listeMedicaments)
        {
            observableListNomMedicaments.add(medicament.getNom());
        }
        medicamentText.setItems(observableListNomMedicaments);
    }
    public boolean validerIdConsultation(int id)
    {
        if(id==0)
        {
            this.notifierError("Le champ 'Identifiant Consultation' est un champ obligatoire");
            return false;
        }
        return true;
    }
    public boolean validerValidier(int validite)
    {
        if(validite < 0 )
        {
            this.notifierError("Le champ 'Validite' doit etre un entier positif");
            return false;
        }
        return true;
    }
    public boolean validerNomMedicament(String nomMedicament)
    {
        if(nomMedicament.isEmpty())
        {
            this.notifierError("Le champ 'Nom Medicament' est un champ obligatoire");
            return false;
        }
        return true;
    }
	public boolean validerEmail(String email)
	{
		 String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                          "[a-zA-Z0-9_+&*-]+)*@" +
                          "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
    public void viderChamps()
    {
        idConsultationText.setValue(null);
        validiteText.setText("");
        medicamentText.setValue(null);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ajouterIdConsultationChoiceBox();
        ajouterNomMedicamentsChoiceBox();
        remplireChoiceBoxTri();
        afficherOrdonnance();
    }

    public void remplireChoiceBoxTri()
    {
        ObservableList<String> observableListCritere = FXCollections.observableArrayList(listeCritere);
        ObservableList<String> observableListOrdre = FXCollections.observableArrayList(listeOrdre);
        critereChoice.setItems(observableListCritere);
        ordreChoice.setItems(observableListOrdre);
    }
    
}
