package GUI.Client;

import Services.ArticleService;
import Entity.Article;
import Entity.Commentaire;
import GUI.Admin.Test;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListeArticleFront implements Initializable {

    @FXML
    private VBox content;

    @FXML
    private TextField testReportSearchTv;

    @FXML
    private Label searchResultNumber;

    @FXML
    private ListView<HBox> testReportListView;
    int count = 0;
    private TableView<Commentaire> commentaireTable;

    private int articleId;
    private TableView<Article> articleTable;
    ArrayList<Article> ArticleArrayList;
    ArticleService produitDAO = new ArticleService();
    @FXML
    private Button backID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Article selectedArticle = articleTable.getSelectionModel().getSelectedItem();
        int articleId = selectedArticle.getId();
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentaireListe.fxml"));

            Parent root = loader.load();

        CommentaireListe secondController = loader.getController();
        secondController.setId(articleId);
        } catch (IOException e) {
    System.err.println("Erreur lors du chargement de la vue CommentaireListe.fxml : " + e.getMessage());
}
         */
        ArticleArrayList = new ArrayList<>();
        testReportListView.getItems().clear();

        try {
            ArticleArrayList = (ArrayList<Article>) new ArticleService().DisplayAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Article article : ArticleArrayList) {
            HBox hBox = createCard(article);

            testReportListView.getItems().add(hBox);
        }
    }

    @FXML
    void onSearchBtnClick(ActionEvent event) throws SQLException {
        ArticleArrayList = new ArrayList<>();
        String str = testReportSearchTv.getText();
        if (str.equals("")) {
            getArticles();
        }
        testReportListView.getItems().clear();
        count = 0;
        ArticleArrayList = (ArrayList<Article>) new ArticleService().searchArticles(str); // Correction: appel à la méthode searchArticles() de la classe ArticleService
        searchResultNumber.setText(ArticleArrayList.size() + " SEARCH RESULTS FOUND");
        for (Article article : ArticleArrayList) { // Correction : boucle sur ArticleArrayList pour créer une carte pour chaque article
            HBox card = createCard(article);
            card.setId(Integer.toString(article.getId())); // Correction : définition de l'ID de la carte comme l'ID de l'article associé
            testReportListView.getItems().add(card);
        }
    }

    private void GeneratePdf() throws IOException {
        double x = Math.random() * (100 - 999);
        String xchar = Double.toString(x);
        String name = "Nom" + xchar;
        ArticleService es = new ArticleService();
        try {
            es.GeneratePdf(name);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        // JarSigningData Notifications = null;

        String path = "C:\\Users\\USER\\Desktop\\mohamedproject" + name + ".pdf";
        File f = new File(path);

    }

    public HBox createCard(Article article) {
        String titre = article.getTitre().toUpperCase();
        String articledesc = article.getArticle_desc().toUpperCase();

        HBox hBox = new HBox();
        hBox.getStyleClass().add("card-background");
        hBox.setPadding(new Insets(10.0d, 20.0d, 10.0d, 20.0d));
        hBox.setSpacing(20);
        hBox.setPrefHeight(100);
        hBox.setPrefWidth(300);

        ArticleService articleService = new ArticleService();
        int index = count;

        ImageView icon = new ImageView("/resources/icons/art.png");
        icon.setImage(new Image("/resources/icons/art.png"));
        icon.setFitWidth(70);
        icon.setFitHeight(70);

        VBox vBox = new VBox();

        vBox.setSpacing(10);
        vBox.setPrefWidth(500);

        Label nameLabel = new Label(titre);
        nameLabel.getStyleClass().add("text-sub-heading");
        Label dateLabel = new Label(articledesc);

        Button prescriptionBtn = new Button();

        prescriptionBtn.setText("Comments");
        prescriptionBtn.setPrefWidth(140);
        prescriptionBtn.getStyleClass().add("button-tertiary-small");
        Button prescriptionBtn1 = new Button();

        prescriptionBtn1.setText("Pdf");
        prescriptionBtn1.setPrefWidth(100);
        prescriptionBtn1.getStyleClass().add("button-text-only-small-red");
        prescriptionBtn1.setOnAction(event
                -> {
            try {
                // Create an HTML string
                String html = "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "\t<title></title>\n"
                        + "\t<style>\n"
                        + "\t\tbody {\n"
                        + "\t\t\tbackground-color: lightblue;\n"
                        + "\t\t\tfont-family: Arial, sans-serif;\n"
                        + "\t\t\tfont-size: 16px;\n"
                        + "\t\t\tcolor: white;\n"
                        + "\t\t\tmargin: 0;\n"
                        + "\t\t\tpadding: 0;\n"
                        + "\t\t}\n"
                        + "\t\th1 {\n"
                        + "\t\t\tfont-size: 36px;\n"
                        + "\t\t\tcolor: darkblue;\n"
                        + "\t\t\tmargin: 20px;\n"
                        + "\t\t\tpadding: 10px;\n"
                        + "\t\t\tborder: 3px solid darkblue;\n"
                        + "\t\t\tbackground-color: white;\n"
                        + "\t\t\ttext-align: center;\n"
                        + "\t\t}\n"
                        + "\t\tp {\n"
                        + "\t\t\tmargin: 20px;\n"
                        + "\t\t\tpadding: 10px;\n"
                        + "\t\t\tborder: 3px solid darkblue;\n"
                        + "\t\t\tbackground-color: white;\n"
                        + "\t\t\tcolor: black;\n"
                        + "\t\t}\n"
                        + "\t</style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "\t<h1>" + article.getTitre() + "</h1>\n"
                        + "<p>" + article.getArticle_desc() + "</p>"
                        + "<p>" + article.getArticle_date() + "</p>"
                        + "<p> Nombre Des Likes :" + article.getNblike() + "</p>"
                        + "<p> Nombre Des DisLikes :" + article.getNbdislike() + "</p>"
                        + "</body>\n"
                        + "</html>\n";

                // Create a new PDF document
                Document document = new Document();

                // Create a PdfWriter object
                PdfWriter writer = null;
                try {
                    writer = PdfWriter.getInstance(document, new FileOutputStream("test_" + article.getTitre() + ".pdf"));
                } catch (DocumentException | FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                // Open the PDF document
                document.open();

                // Convert the HTML to a PDF and add it to the document
                HtmlConverter.convertToPdf(html, writer.getOs());

                // Close the PDF document
                System.out.println("HTML converted to PDF successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        dateLabel.getStyleClass().add("text-sub-heading-light");

        vBox.getChildren().addAll(nameLabel, dateLabel);

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(prescriptionBtn, prescriptionBtn1);
        vBox1.setSpacing(10);
        vBox1.setAlignment(Pos.CENTER_RIGHT);

        ImageView iconL = new ImageView("/resources/icons/like.png");
        iconL.setImage(new Image("/resources/icons/like.png"));
        iconL.setFitWidth(30);
        iconL.setFitHeight(30);

        ImageView iconD = new ImageView("/resources/icons/dis.png");
        iconD.setImage(new Image("/resources/icons/dis.png"));
        iconD.setFitWidth(30);
        iconD.setFitHeight(30);

        Label nameLike = new Label(String.valueOf(article.getNblike()));

        nameLabel.getStyleClass().add("text-sub-heading");
        Label nameDis = new Label(String.valueOf(article.getNbdislike()));
        nameLabel.getStyleClass().add("text-sub-heading");

        Label namecmn = new Label(String.valueOf(article.getNbcomment()));
        nameLabel.getStyleClass().add("text-sub-heading");

        ImageView iconcmn = new ImageView("/resources/icons/cmn.png");
        iconcmn.setImage(new Image("/resources/icons/cmn.png"));
        iconcmn.setFitWidth(30);
        iconcmn.setFitHeight(30);
        // Ajouter un événement de clic sur l'image du commentaire

        iconcmn.setOnMouseClicked(event -> {

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            HBox art = testReportListView.getSelectionModel().getSelectedItem();
            System.out.println(art.getId());
            int g = Integer.parseInt(art.getId());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Admin/test.fxml"));
                Node node = loader.load();

                Test controller = loader.getController();

                controller.setId(g);

                content.getChildren().clear();
                content.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(iconL, nameLike, iconD, nameDis, iconcmn, namecmn);
        hbox.setSpacing(10); // espace entre les deux images

        vBox.getChildren().add(hbox);

        HBox hBox1 = new HBox();
        HBox.setHgrow(hBox1, Priority.ALWAYS);
        hBox1.setAlignment(Pos.CENTER_RIGHT);

        Button viewMoreBtn = new Button();
        viewMoreBtn.setPrefHeight(50);
        viewMoreBtn.setPrefWidth(50);
        viewMoreBtn.setPadding(new Insets(0, 20, 0, 0));

        prescriptionBtn.setOnAction(e -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Client/Ajoutcmn.fxml"));
                Parent root = loader.load();
                AjoutCmn ajoutCmn = loader.getController();
                ajoutCmn.setArticle(article);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        //hBox1.getChildren().add(newLabel);
        Label newLabel = new Label(String.valueOf(article.getArticle_date()));
        newLabel.getStyleClass().add("text-sub-heading");
        newLabel.setPrefWidth(170);
        newLabel.setPadding(new Insets(0, 15, 0, 0));
        hBox1.getChildren().addAll(newLabel, viewMoreBtn);
        hBox.getChildren().addAll(icon, vBox, hBox1, vBox1);
        return hBox;
    }

    private void getArticles() throws SQLException {

        ArticleArrayList = new ArrayList<>();
        testReportListView.getItems().clear();

        count = 0;
        ArticleArrayList = (ArrayList<Article>) new ArticleService().DisplayAll();
        for (Article article : ArticleArrayList) {
            HBox hBox = createCard(article);
            hBox.setId(String.valueOf(article.getId()));

            testReportListView.getItems().add(hBox);
        }
    }

    @FXML
    private void BackToMainPage(ActionEvent event) throws IOException {
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Client/ClientMain.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }

}
