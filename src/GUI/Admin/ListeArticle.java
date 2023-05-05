package GUI.Admin;

import Services.ArticleService;
import Services.CommentaireService;
import Entity.Article;
import Entity.Commentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListeArticle implements Initializable {

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
            HBox hBox = createCard(article.getTitre(), article.getArticle_desc(),
                    article.getArticle_date().toString(), article.getNblike(), article.getNbdislike(), article.getNbcomment());
            hBox.setId(String.valueOf(article.getId()));

            testReportListView.getItems().add(hBox);
        }
    }

    /*@FXML
    void onSearchBtnClick(ActionEvent event) {
        testReportSearchTv.setStyle("-fx-background-image: url('/resources/icons/ic_search.png');");
        testReportListView.getItems().clear();
        ArrayList<Article> recentPatientList = new Article().getTestReport(testReportSearchTv.getText());
        searchResultNumber.setText(recentPatientList.size() + " SEARCH RESULTS FOUND");
        for (int i = 0; i < recentPatientList.size(); i++){
            MedicalTestDetails medicalTestDetails = recentPatientList.get(i);
            HBox hBox = createCard(medicalTestDetails.getDoctorName(),medicalTestDetails.getPatientName(),
                    medicalTestDetails.getTestDate(), i);
            testReportListView.getItems().add(hBox);
        }
    }*/
    @FXML
    void onSearchBtnClick(ActionEvent event) throws SQLException {
        ArticleArrayList = new ArrayList<>();
        String str = testReportSearchTv.getText();
        if (str.equals("")) {
            return;
        }
        testReportListView.getItems().clear();
        count = 0;
        ArticleArrayList = (ArrayList<Article>) new ArticleService().searchArticles(str); // Correction: appel à la méthode searchArticles() de la classe ArticleService
        searchResultNumber.setText(ArticleArrayList.size() + " SEARCH RESULTS FOUND");
        for (Article article : ArticleArrayList) { // Correction : boucle sur ArticleArrayList pour créer une carte pour chaque article
            HBox card = createCard(article.getTitre(), article.getArticle_desc(), String.valueOf(article.getArticle_date()), article.getNblike(), article.getNbdislike(), article.getNbcomment());
            card.setId(Integer.toString(article.getId())); // Correction : définition de l'ID de la carte comme l'ID de l'article associé
            testReportListView.getItems().add(card);
        }
    }

    public HBox createCard(String titre, String articledesc, String date, int like, int dis, int cmn) {
        titre = titre.toUpperCase();
        articledesc = articledesc.toUpperCase();

        HBox hBox = new HBox();
        hBox.getStyleClass().add("card-background");
        hBox.setPadding(new Insets(10.0d, 20.0d, 10.0d, 20.0d));
        hBox.setSpacing(20);
        hBox.setPrefHeight(100);
        hBox.setPrefWidth(300);

        CommentaireService commentaireService = new CommentaireService();
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
        prescriptionBtn.setText("DELETE");
        prescriptionBtn.setPrefWidth(180);
        prescriptionBtn.getStyleClass().add("button-tertiary-small");
        Button prescriptionBtn1 = new Button();
        prescriptionBtn1.setText("Modifier");
        prescriptionBtn1.setPrefWidth(180);
        prescriptionBtn1.setOnAction(event -> {

            HBox art = testReportListView.getSelectionModel().getSelectedItem();
            System.out.println(art);

            int g = ArticleArrayList.get(index).getId();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Admin/Modifier.fxml"));
                Node node = loader.load();

                Modifier controller = loader.getController();

                controller.setId(ArticleArrayList.get(index));

                content.getChildren().clear();
                content.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        prescriptionBtn1.getStyleClass().add("button-text-only-small-red");

        ArrayList<Commentaire> RdvArrayList = new ArrayList<>();

        prescriptionBtn.setOnAction(e -> {
            System.out.println("u clicked here ");
            System.out.println(ArticleArrayList.get(index).getId());
            articleService.delete(ArticleArrayList.get(index).getId());

            System.out.println("deellteed ");
            try {
                getArticles();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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

        Label nameLike = new Label(String.valueOf(like));

        nameLabel.getStyleClass().add("text-sub-heading");
        Label nameDis = new Label(String.valueOf(dis));
        nameLabel.getStyleClass().add("text-sub-heading");

        Label namecmn = new Label();
        nameLabel.getStyleClass().add("text-sub-heading");

        ImageView iconcmn = new ImageView("/resources/icons/cmn.png");
        iconcmn.setImage(new Image("/resources/icons/cmn.png"));
        iconcmn.setFitWidth(30);
        // Ajouter un événement de clic sur l'image du commentaire
        iconcmn.setOnMouseClicked(event -> {

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            HBox art = testReportListView.getSelectionModel().getSelectedItem();
            System.out.println(art.getId());
            int g = Integer.parseInt(art.getId());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
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

        //hBox1.getChildren().add(newLabel);
        Label newLabel = new Label(date);
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

        ArticleArrayList = (ArrayList<Article>) new ArticleService().DisplayAll();
        for (Article article : ArticleArrayList) {
            HBox hBox = createCard(article.getTitre(), article.getArticle_desc(),
                    article.getArticle_date().toString(), article.getNblike(), article.getNbdislike(), article.getNbcomment());
            hBox.setId(String.valueOf(article.getId()));
            testReportListView.getItems().add(hBox);
        }
    }

    public ListeArticle() {
        this.count++;
    }

    @FXML
    private void BackToMainPage(ActionEvent event) throws IOException {
         Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/Admin/AdminMain.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }
}
