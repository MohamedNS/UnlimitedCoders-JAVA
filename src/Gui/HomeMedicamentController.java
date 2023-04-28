/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import Entity.Medicament;
import Services.ServiceMedicament;
import Services.ServicePDF;
import Utils.MyConnection;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.sql.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

    
    public static final  List<String> listeCriteres  = Arrays.asList("Identifiant","Nom","Dosage","Prix");
    public static final List<String> listeOrdre = Arrays.asList("Croissant","Decroissant");

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
	@FXML
	public Button btnExcel;
    @FXML
    public Button btnTrier;

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
    @FXML
    private ChoiceBox critereChoice;
    @FXML
    private ChoiceBox ordreChoice;

	//Interface Statistiques
	@FXML
	private Label moyennePrixLabel;
	@FXML
	private PieChart plusutilisePi;
	@FXML
	private BarChart<String,Number> prixbar;

	
	private Connection cnx;

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
        if(nomText.getText().isEmpty() || dosageText.getText().isEmpty() || prixText.getText().isEmpty() || descriptionText.getText().isEmpty())
        {
            this.notifierError("Operation Ajout Refusee");
        }
        else
        {
			String nom = nomText.getText();
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
            if (m == null)
            {
                this.notifierError("Veuillez sélectionner un médicament avant");
            }
            else
            {
                if(this.notifierConfirmation("Voullez vous modifier ce médicament"))
                {
                    ModifierMedicamentController mc = loader.getController();
                    mc.setTextFiels(m);
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
    public void btnSupprimer(ActionEvent evt)
    {
        System.out.println("Supprimer Medicament Click");
        ServiceMedicament sv = new ServiceMedicament();
        Medicament m = (Medicament) medicamentTable.getSelectionModel().getSelectedItem();
        if(m == null)
        {
            this.notifierError("Veuillez sélectionner un médicament avant");
        }
        else
        {
            if(this.notifierConfirmation("Veuillez vous supprimer ce médicament"))
            {
                sv.supprimerMedicament(m.getId());
                this.notifier("Suppression");
                afficherListeMedicament();
            }
        }
    }
    @FXML
    private void btnPdf(ActionEvent evt) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
        System.out.println("Boutton PDF Click");
        ServiceMedicament sv = new ServiceMedicament();
        ServicePDF pdf = new ServicePDF();
        List<Medicament> listeMedicaments = sv.afficherMedicament();
        pdf.genererPdfMedicament("Medicament", listeMedicaments);
    }

	@FXML
	private void btnExcel(ActionEvent evt)
	{
		System.out.println("Boutton Excel Click");	
	}
    @FXML
    private void btnTrier(ActionEvent evt)
    {
        String critere = (String) critereChoice.getValue();
        String ordre = (String) ordreChoice.getValue();
        System.out.println("Boutton Trier Click");
        if(critere == null || ordre == null)
        {
            this.notifierError("Remplissez les Champs 'Critere' et 'Ordre' avant");
        }
        else
        {
            ServiceMedicament sv = new ServiceMedicament();
            List<Medicament> listeMedicaments = sv.trierMedicament(critere, ordre);
            ObservableList<Medicament> list = FXCollections.observableArrayList(listeMedicaments);
            idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomColonne.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dosageColonne.setCellValueFactory(new PropertyValueFactory<>("dosage"));
            prixColonne.setCellValueFactory(new PropertyValueFactory<>("prix"));
            descriptionColonne.setCellValueFactory(new PropertyValueFactory<>("description"));
            medicamentTable.setItems(list);

        }

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

	public void remplirePiChart()
	{
		cnx = MyConnection.getInstance().getConnection();
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		try {
			String req = "SELECT COUNT(*) AS count, medicament.nom FROM medicament INNER JOIN ordonnance_medicament ON medicament.id = ordonnance_medicament.medicament_id GROUP BY medicament.nom";
			PreparedStatement pt = cnx.prepareStatement(req);
			ResultSet rs = pt.executeQuery();
			while(rs.next())
			{
				data.add(new PieChart.Data(rs.getString("nom"), rs.getInt("count")));
			}
			plusutilisePi.setTitle("Médicaments plus prescrits");
			plusutilisePi.setData(data);

	    } catch (SQLException ex) {
			System.out.println(ex.getMessage());
	    }
	}
	public void remplirePrixBar()
	{
		cnx = MyConnection.getInstance().getConnection();
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		int count = 0;
		float rangeStart = 0;
		float rangeEnd = 10;

		//BarChart<String,Number> pribar = new BarChart<>(xAxis,yAxis);
		//System.out.println("BarChart : "+BarChart.class.toString());
		prixbar.setTitle("Médicament Par Prix");
		xAxis.setLabel("Marge des prix");
		yAxis.setAccessibleHelp("Nombre des médicmaents");

		try{
			String req = "SELECT prix from medicament order by prix";
			Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery(req);
			XYChart.Series<String,Number> series = new XYChart.Series<>();

			while(rs.next())
			{
				//System.out.println("Prix : "+String.valueOf(rs.getFloat("prix")));
				float prix = rs.getFloat("prix");
				if(prix >= rangeStart && prix<rangeEnd)
				{
					count++;
				}
				else
				{
					series.getData().add(new XYChart.Data<>(String.format("%.1f-%.1f",rangeStart,rangeEnd),count));
					count = 1;
					rangeStart = rangeEnd;
					rangeEnd += 10;
				}
			}
			prixbar.getData().add(series);

		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}

		
	}
	public void afficherStatistiques()
	{
		ServiceMedicament sv = new ServiceMedicament();
		String moyenne = String.format("%.2f", sv.calculerMoyennePrix());
		moyennePrixLabel.setText(moyenne+" TND");
		this.remplirePiChart();
		this.remplirePrixBar();
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
    public void remplireChoiceBoxTri()
    {
        ObservableList<String> observableListCritere = FXCollections.observableArrayList(listeCriteres);
        ObservableList<String> observableListOrdre = FXCollections.observableArrayList(listeOrdre);
        critereChoice.setItems(observableListCritere);
        ordreChoice.setItems(observableListOrdre);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remplireChoiceBoxTri();
        afficherListeMedicament();
		afficherStatistiques();
    }
    
}
