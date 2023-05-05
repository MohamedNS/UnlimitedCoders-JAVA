package GUI.Client;

import Services.CommentaireService;
import Entity.Article;
import Entity.Commentaire;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class AjoutCmn implements Initializable {

    private int articleId;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab healthCondTab;
    @FXML
    private Button backID;

    public AjoutCmn(Commentaire commentaire) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("hneee id");
        System.out.println(id);
    }

    @FXML
    private DatePicker date;
    @FXML
    private TextArea titre;

    CommentaireService commentaireDao = new CommentaireService();

    Article article;
    int id;
    ArrayList<Commentaire> CommentaireArrayList;

    public void setId(int id) {
        this.id = id;

        System.out.println(id);

        //commentaireArrayList = new ArrayList<>();
        //  try {
        // CommentaireArrayList = (ArrayList<Commentaire>) new CommentaireService().insertId(new Commentaire(),id);
        // } catch (SQLException e) {
        //   throw new RuntimeException(e);
        // }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    private List<String> badWords;

    @FXML
    private void Ajouter() throws MessagingException {

        // Initialize the list of badwords
        badWords = new ArrayList<String>();
        badWords.add("fuck");
        badWords.add("pute");
        System.out.println(badWords);

        if (!validateInput() | containsBadWord(titre.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your comment contains inappropriate language.");
            alert.showAndWait();

        } else {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = date.getValue();

            Date utilDate = java.sql.Date.valueOf(localDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Commentaire a = new Commentaire(titre.getText(), sqlDate);
            System.out.println("addeddd ");
            System.out.println(a);
            commentaireDao.insert(a, this.article);
            System.out.println("addeddd ");

            String host = "smtp.gmail.com";
            String utilisateur = "mohamed.zrig@esprit.tn";
            String pass = "anapachawowo";
            String SendTo = "mohamed.zrig@esprit.tn";
            String from = "mohamed.zrig@esprit.tn";
            String Subject = "Information à propos d'ajout d'un Commentaire";
            String textMessage = "<html><body style='font-family: Arial, sans-serif; color: #333;'><h1 style='color: #0066CC;'>Un nouveau Commentaire a été Ajouté " + " /n " + " contenu :" + a.getCommentairecontenu() + " Date : " + a.getCommentairedate() + " .</h1><p style='color: #666;'>Cordialement,<br>Equipe Estate Roomies</p></body></html>";
            boolean seesionDebug = false;
            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            // Security.addProvider(new Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(seesionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(SendTo)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(Subject);
            msg.setContent(textMessage, "text/html");

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, utilisateur, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");

        }
    }

    private boolean validateInput() {
        if (titre.getText().isEmpty()) {
            showAlert("Titre field Invalid.");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error check your inputs");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    ArrayList<Article> ArticleArrayList;


    /*  public void setId(int id,Commentaire c) {
        this.id = id;

        System.out.println(id);


        ArticleArrayList = new ArrayList<>();
        testReportListView.getItems().clear();

        try {
            ArticleArrayList = (ArrayList<Article>) ArticleService.ajouterCommentaire(id,c.getCommentairecontenu(),c.getCommentairedate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


      /*  try {
            // Get the list of comments for the selected article
            List<Commentaire> commentaires = new CommentaireService().getCommentairesForArticle(id);

            // Update the table view with the comments
            commentaireTable.getItems().setAll(commentaires);

        } catch (SQLException e) {
            // Handle the exception
        }*/
    public AjoutCmn() {

    }

    private boolean containsBadWord(String comment) {
        for (String word : comment.split("\\s+")) {
            if (badWords.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void handleAjouterButton(ActionEvent event) throws SQLException {

        // Initialize the list of badwords
        badWords = new ArrayList<String>();
        badWords.add("fuck");
        badWords.add("pute");
        System.out.println(badWords);

        // Récupérer le contenu du commentaire
        String contenu = titre.getText();
        LocalDate d = date.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(d);

        if (containsBadWord(contenu)) {
            // Display an error message if a badword is found
            Alert alert = new Alert(Alert.AlertType.ERROR, "Your comment contains inappropriate language.");
            alert.showAndWait();
        } else {
            Commentaire commentaire = new Commentaire(contenu, sqlDate, articleId);

            // Enregistrer le commentaire dans la base de données
            CommentaireService commentaireService = new CommentaireService();
            commentaireService.insert(commentaire);

            // Submit the comment if no badword is found
            // TODO: Submit the comment to your backend server or database
        }
    }

    // Créer le commentaire avec l'ID de l'article sélectionné

    @FXML
    private void BackToMainPage(javafx.event.ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Client/ClientMain.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }
}
