
 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.Image;
import Entity.Facteur;
import Entity.Medicament;
import Entity.Ordonnance;
import static java.awt.SystemColor.text;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import Services.FacteurGrud;
import Services.ServiceMail;
import Services.ServiceMedicament;
import Utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterfacturController implements Initializable {

   
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    private TextField tfidpatient;
    @FXML
    private TextField tfcin;
    @FXML
    private TextField tfprix;
    @FXML
    private Button tfsave;
    @FXML
    private ComboBox<Integer> tfdosage;
    @FXML
    private ComboBox<String> tfmed;
    @FXML
    private AnchorPane tfnommed;
    @FXML
    private TextArea tftext;
private int ts = 0;
private String allmed = "";
private String alldosage = "";
private  String diretorio;
   Connection cnx;
    @FXML
    private Button next;
    @FXML
    private TextField tfmail;
    @FXML
    private Button mail;

    @FXML
    private Button Btnqr;
    @FXML
    private AnchorPane tfnommed1;
    @FXML
    private TextField tfnom1;
    @FXML
    private TextField tfprenom1;
    @FXML
    private TextField tfcin1;
    @FXML
    private TextField tfprix1;
    @FXML
    private Button tfsave1;
    @FXML
    private ComboBox<?> tfdosage1;
    @FXML
    private ComboBox<?> tfmed1;
    @FXML
    private TextArea tftext1;
    @FXML
    private TextField tfmail1;
    /**
     * Initializes the controller class.
     */
    
    public void notifier(String operation)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Facur");
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
    
    public void initialize(URL url, ResourceBundle rb) {
         ObservableList<String> observableListNomMedicaments = FXCollections.observableArrayList();
          ObservableList<Integer> observableListdosage = FXCollections.observableArrayList();
        ServiceMedicament sv = new ServiceMedicament();
        List<Medicament> listeMedicaments = sv.afficherMedicament();
        for(Medicament medicament: listeMedicaments)
        {
            observableListNomMedicaments.add(medicament.getNom());
            observableListdosage.add(medicament.getDosage());
        }
         tfmed.setItems(observableListNomMedicaments);
        tfdosage.setItems(observableListdosage);
      diretorio = new File("").getAbsolutePath();
      diretorio +=File.separator+"QRDIR";
      File file = new File(diretorio);
      if(!file.isDirectory()){
          file.mkdir();
      }
        // TODO
    }    

    @FXML
    
    private List<String> Select(ActionEvent event) throws SQLException {
     cnx = MyConnection.getInstance().getConnection();
      List<String> result = new ArrayList<>();
   String s = tfmed.getSelectionModel().getSelectedItem().toString();
     System.out.println("med is"+s);
   String t=tfdosage.getSelectionModel().getSelectedItem().toString();
   String text= s+"  "+t;
      int price = 0;
      String r;
      
     
      tftext.setText(text+"\n");
        
           
            
 if(s!="")
            {
                 FacteurGrud sv = new FacteurGrud();
        price = sv.getprix(s);
           ts += price;
           allmed += s + "\n";
            alldosage  += t + "\n";
           r=  String.valueOf(ts);
           tfprix.setText(r);
            }
      System.out.println("price is"+ts);
      System.out.println("all med is"+allmed+"\n");
      System.out.println("dosage is"+alldosage+"\n");
     
    result.add(allmed);
    result.add(alldosage);
      return result;
    }

    @FXML
    private void save(ActionEvent event) {
       System.out.println("all med after"+allmed+"\n");
      System.out.println("dosage after"+alldosage+"\n");
      String cin =tfcin.getText();
        String nom =tfnom.getText();
        String prenom =tfprenom.getText();
        
        String nom_med =allmed;
        String dosage =alldosage;
        int prix  =Integer.parseInt (tfprix.getText());
        if(tfcin.getText().isEmpty() ||tfnom.getText().isEmpty() || tfprenom.getText().isEmpty()  ||tfprix.getText().isEmpty() ||allmed.isEmpty() ||alldosage.isEmpty())
        {
            this.notifierError("Operation Ajout refusee.Remplissez tous les champs");
        }
   
        Facteur c =new Facteur(cin,nom,prenom,0,0,nom_med,dosage,prix);
        FacteurGrud pc = new FacteurGrud();
        pc.ajouterfacteur(c);
        System.out.println("factur rest "+c+"\n");
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
  public void show()
    {
        try{
            System.out.println("Boutton Retour Click");
            Parent loader = FXMLLoader.load(getClass().getResource("Afficherfactur.fxml"));
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
    @FXML
    private void btnnext(ActionEvent event) throws IOException {
         Parent mainPageParent = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }

    @FXML
    public void btnEnvoyer(ActionEvent evt)
	{
		ServiceMail sv = new ServiceMail();
                 FacteurGrud st = new FacteurGrud();     
                   List<Facteur> listeFacteur = st.afficherfacteurs();
		System.out.println("Bouttone Mail Click");
		if(tfmail.getText().isEmpty() ||tfcin.getText().isEmpty() ||tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() ||tfidpatient.getText().isEmpty() ||tfprix.getText().isEmpty() ||allmed.isEmpty() ||alldosage.isEmpty() )
		{
			this.notifierError("Avant d'envoyer un Mail, Remplissez tous les champs");
		}
		else
		{
			if(this.validerEmail(tfmail.getText()))
			{
				String adresseTo = tfmail.getText();
				 for(Facteur facteur : listeFacteur) {
                sv.notifierFacteur(adresseTo, facteur);
            }
                              

				
				this.notifier("Message envoyé avec succées vers : "+adresseTo);
			}
			else
			{
				this.notifierError("L'adresse mail : ' "+tfmail.getText()+"' n'est pas une adresse mail valide");
			}
		}
		

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

    @FXML
    private void gereQRcode(ActionEvent event) throws FileNotFoundException, IOException {
         String nom =tfnom.getText();
        String qr="";
        qr +="cin"+tfcin.getText()+"\n";
        qr +="nom"+tfnom.getText()+"\n";
        qr +="prenom"+tfprenom.getText()+"\n";
        qr +="id_patient"+Integer.parseInt(tfidpatient.getText())+"\n";
        qr +="nom_med"+allmed+"\n";
        qr +="dosage"+alldosage+"\n";
        qr +="prix"+Integer.parseInt (tfprix.getText())+"\n";
         
        FileOutputStream fout = new FileOutputStream(diretorio+File.separator+nom+".jpg");
        ByteArrayOutputStream bos = QRCode.from(qr).to(ImageType.JPG).stream();
        fout.write(bos.toByteArray());
        bos.close();
        fout.close();
        fout.flush();
        
        
    }

}
