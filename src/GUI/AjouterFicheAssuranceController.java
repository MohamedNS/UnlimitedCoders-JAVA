/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.FicheAssurance;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import Services.FicheAssuranceGrud;
import Services.ServiceMail;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterFicheAssuranceController implements Initializable {

    @FXML
    private AnchorPane tfnommed;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfcin;
    @FXML
    private TextField tftotal;
    @FXML
    private Button tfsave;
    @FXML
    private Button next;
    @FXML
    private TextField tfmail;
    @FXML
    private Button mail;
    @FXML
    private Button Btnqr;
    @FXML
    private TextField tfhonoraires;
    @FXML
    private TextField tfmatriculefiscal;
    @FXML
    private TextField tfmatriculecnam;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfdesignation;

    /**
     * Initializes the controller class.
     */
    public void notifier(String operation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Facur");
        alert.setHeaderText(null);
        alert.setContentText("Operation " + operation + " Effectue avec success");
        alert.show();
    }

    public void notifierError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Operation Refusee");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    public boolean notifierConfirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent event) {
        FicheAssuranceGrud sc = new FicheAssuranceGrud();

        if (tfcin.getText().isEmpty() || tfnom.getText().isEmpty() || tfprenom.getText().isEmpty() || tfadresse.getText().isEmpty() || tfmatriculecnam.getText().isEmpty() || tfmatriculefiscal.getText().isEmpty() || tfhonoraires.getText().isEmpty()) {
            this.notifierError("Operation Ajout refusee.Remplissez tous les champs");
        }
        String cin = tfcin.getText();
        String nom = tfnom.getText();
        String prenom = tfprenom.getText();
        String addresse = tfadresse.getText();
        String matricule_cnam = tfmatriculecnam.getText();
        int matricule_fiscal = Integer.parseInt(tfmatriculefiscal.getText());
        int honoraires = Integer.parseInt(tfhonoraires.getText());
        String designation = tfdesignation.getText();
        LocalDate date = tfdate.getValue();
        int total = Integer.parseInt(tftotal.getText());
        System.out.println("FicheAssurance est " + "" + "\n");
        if (this.validerNom(nom) && this.validerprenom(prenom) && this.validercin(cin) && this.valideraddresse(addresse) && this.validermatricule_cnam(matricule_cnam) && this.validerhonoraires(honoraires) && this.validerhonoraires(matricule_fiscal)) {
            FicheAssurance c = new FicheAssurance();
            c.setCin(cin);
            c.setNom(nom);
            c.setPrenom(prenom);
            c.setAddresse(addresse);
            c.setMatricule_cnam(matricule_cnam);
            c.setMatricule_fiscal(matricule_fiscal);
            c.setHonoraires(honoraires);
            c.setDate(java.sql.Date.valueOf(date));
            c.setTotal(total);
            sc.ajouterFicheAssurance(c);
            FicheAssuranceGrud pc = new FicheAssuranceGrud();
            pc.ajouterFicheAssurance(c);
            this.notifier("Ajout");
            this.viderChamps();
            System.out.println("FicheAssurance est " + c + "\n");
        }
    }

    public void viderChamps() {
        tfcin.setText("");
        tfnom.setText("");
        tfprenom.setText("");
        tfadresse.setText("");
        tfmatriculecnam.setText("");
        tfmatriculefiscal.setText("");
        tfhonoraires.setText("");
        tfdesignation.setText("");
        tftotal.setText("");
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
    private void btnEnvoyer(ActionEvent event) {
        ServiceMail sv = new ServiceMail();
        FicheAssuranceGrud st = new FicheAssuranceGrud();
        List<FicheAssurance> listeFacteur = st.afficherFicheAssurances();
        System.out.println("Bouttone Mail Click");
        if (tfmail.getText().isEmpty() || tfcin.getText().isEmpty() || tfnom.getText().isEmpty() || tfprenom.getText().isEmpty()) {
            this.notifierError("Avant d'envoyer un Mail, Remplissez tous les champs");
        } else if (this.validerEmail(tfmail.getText())) {
            String adresseTo = tfmail.getText();
            for (FicheAssurance facteur : listeFacteur) {
                sv.notifierAssurance(adresseTo, facteur);
            }

            this.notifier("Message envoyé avec succées vers : " + adresseTo);
        } else {
            this.notifierError("L'adresse mail : ' " + tfmail.getText() + "' n'est pas une adresse mail valide");
        }
    }

    public boolean validerEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    private void gereQRcode(ActionEvent event) {
    }

    public boolean validerNom(String nom) {
        if (nom.isEmpty()) {
            this.notifierError("'Nom ' est un champ obligatoire");
            return false;
        }
        return true;
    }

    public boolean validercin(String cin) {
        if (cin.isEmpty()) {
            this.notifierError("'cin ' est un champ obligatoire");
            return false;
        }
        return true;
    }

    public boolean validerprenom(String prenom) {
        if (prenom.isEmpty()) {
            this.notifierError("'Prenom' est un champ obligatoire");
            return false;
        }
        return true;
    }

    public boolean valideraddresse(String addresse) {
        if (addresse.isEmpty()) {
            this.notifierError("'Addresse' est un champ obligatoire");
            return false;
        }
        return true;
    }

    public boolean validermatricule_cnam(String matricule_cnam) {
        if (matricule_cnam.isEmpty()) {
            this.notifierError("'Matricule_Cnam' est un champ obligatoire");
            return false;
        }
        return true;
    }

    public boolean validerdesignation(String designation) {
        if (designation.isEmpty()) {
            this.notifierError("'Designation' est un champ obligatoire");
            return false;
        }
        return true;
    }

    public boolean validermatricule_fiscal(int matricule_fiscal) {
        if (matricule_fiscal < 0) {
            this.notifierError("Le champ 'matricule_fiscal' doit etre un entier positif");
            return false;
        }
        return true;

    }

    public boolean validerhonoraires(int honoraires) {
        if (honoraires < 0) {
            this.notifierError("Le champ 'honoraires' doit etre un entier positif");
            return false;
        }
        return true;

    }

}
