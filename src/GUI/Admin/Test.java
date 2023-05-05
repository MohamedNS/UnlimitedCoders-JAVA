package GUI.Admin;

import Services.CommentaireService;
import Entity.Commentaire;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Test implements Initializable {

    @FXML
    private TextField testReportSearchTv;
    @FXML
    private Label searchResultNumber;
    @FXML
    private Button backID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CommentaireArrayList = new ArrayList<>();
        testReportListView.getItems().clear();

        try {
            CommentaireArrayList = (ArrayList<Commentaire>) new CommentaireService().DisplayAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Commentaire article : CommentaireArrayList) {
            HBox hBox = createCard(article.getCommentairecontenu(), String.valueOf(article.getCommentairedate()),
                    article.getArticle_id());
            hBox.setId(String.valueOf(article.getId()));
            System.out.println(hBox.getId());

            testReportListView.getItems().add(hBox);

        }
    }

    @FXML
    private ListView<HBox> testReportListView;

    ArrayList<Commentaire> CommentaireArrayList;

    int id;

    public void setId(int id) {
        this.id = id;

        System.out.println(id);

        CommentaireArrayList = new ArrayList<>();
        testReportListView.getItems().clear();

        try {
            CommentaireArrayList = (ArrayList<Commentaire>) new CommentaireService().getCommentairesForArticle(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Commentaire article : CommentaireArrayList) {
            HBox hBox = createCard(article.getCommentairecontenu(), String.valueOf(article.getCommentairedate()),
                    article.getArticle_id());
            hBox.setId(String.valueOf(article.getId()));
            System.out.println(hBox.getId());

            testReportListView.getItems().add(hBox);

            /*  try {
            // Get the list of comments for the selected article
            List<Commentaire> commentaires = new CommentaireService().getCommentairesForArticle(id);

            // Update the table view with the comments
            commentaireTable.getItems().setAll(commentaires);

        } catch (SQLException e) {
            // Handle the exception
        }*/
        }
    }

    int count = 0;

    public HBox createCard(String titre, String date, int like) {
        int index = count;
        titre = titre.toUpperCase();

        HBox hBox = new HBox();
        hBox.getStyleClass().add("card-background");
        hBox.setPadding(new Insets(10.0d, 20.0d, 10.0d, 20.0d));
        hBox.setSpacing(20);
        hBox.setPrefHeight(100);
        hBox.setPrefWidth(300);

        ImageView icon = new ImageView("/resources/icons/cmn.png");
        icon.setImage(new Image("/resources/icons/cmn.png"));
        icon.setFitWidth(70);
        icon.setFitHeight(70);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(5);
        vBox.setPrefWidth(500);

        Label nameLabel = new Label(titre);
        nameLabel.getStyleClass().add("text-sub-heading");
        Label dateLabel = new Label(date);

        dateLabel.getStyleClass().add("text-sub-heading-light");
        vBox.getChildren().addAll(nameLabel, dateLabel);

        JFXButton prescriptionBtn = new JFXButton();
        prescriptionBtn.setText("DELETE");
        prescriptionBtn.setPrefWidth(180);
        prescriptionBtn.getStyleClass().add("button-tertiary-small");
        CommentaireService articleService = new CommentaireService();
        prescriptionBtn.setOnAction(e -> {
            System.out.println("u clicked here ");
            articleService.delete(CommentaireArrayList.get(index).getId());

            System.out.println("deellteed ");

            try {
                getComment();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox hBox1 = new HBox();
        HBox.setHgrow(hBox1, Priority.ALWAYS);
        hBox1.setAlignment(Pos.CENTER_RIGHT);

        JFXButton viewMoreBtn = new JFXButton();
        viewMoreBtn.setPrefHeight(50);
        viewMoreBtn.setPrefWidth(50);
        viewMoreBtn.setPadding(new Insets(0, 20, 0, 0));


        /*  Image img = new Image("/resources/icons/dis.png");
        ImageView view = new ImageView(img);
        viewMoreBtn.setGraphic(view);
        nameLabel.getStyleClass().add("text-sub-heading");

        Image imgL = new Image("/resources/icons/like.png");
        ImageView view1 = new ImageView(imgL);
        viewMoreBtn.setGraphic(view1);
        nameLabel.getStyleClass().add("text-sub-heading");*/
        //hBox1.getChildren().add(newLabel);
        Label newLabel = new Label(date);
        newLabel.getStyleClass().add("text-sub-heading");
        newLabel.setPrefWidth(170);
        newLabel.setPadding(new Insets(0, 15, 0, 0));
        hBox1.getChildren().addAll(newLabel, viewMoreBtn, prescriptionBtn);
        hBox.getChildren().addAll(icon, vBox, hBox1);
        return hBox;
    }

    private void getComment() throws SQLException {

        CommentaireArrayList = new ArrayList<>();
        testReportListView.getItems().clear();

        CommentaireArrayList = (ArrayList<Commentaire>) new CommentaireService().DisplayAll();
        CommentaireArrayList.stream().map((article) -> {
            HBox hBox = createCard(article.getCommentairecontenu(), String.valueOf(article.getCommentairedate()), article.getId());
            hBox.setId(String.valueOf(article.getId()));
            return hBox;
        }).forEach((hBox) -> {
            testReportListView.getItems().add(hBox);
        });
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
