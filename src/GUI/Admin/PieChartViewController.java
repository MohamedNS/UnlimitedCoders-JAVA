package GUI.Admin;

import Services.ArticleService;
import Entity.Article;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class PieChartViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PieChart pieChart;
    ObservableList<Data> list= FXCollections.
            observableArrayList();
    @FXML
    private PieChart pieChartLikes;
    @FXML
    private PieChart pieChartDislikes;
    @FXML
    private Button backID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     /*   PromotionDao pdao=PromotionDao.getInstance();
        List<Promotion> pers=pdao.displayAll();
        for(Promotion p:pers) {
            list.addAll(
                //new Data(""+p.getPourcentage(), 12.0),
                new Data(""+p.getPourcentage(), 12.0)
        );
        }
        pieChart.setAnimated(false);
        pieChart.setData(list);

    }*/
        ArticleService pdao = new ArticleService();
        List<Article> pers;
        try {
            pers = pdao.DisplayAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Créer une liste de couleurs pour chaque pourcentage
        List<Color> colors = Arrays.asList(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
        int like = 0;
        int dis = 0;
        for (int i = 0; i < pers.size(); i++) {
            Article p = pers.get(i);
            like = p.getNblike() + like;
            dis = p.getNbdislike() + dis;
        }

// Créer les données pour le graphique à secteurs
        ObservableList<Data> pieChartData = FXCollections.observableArrayList(
                new Data("Likes : "+ like,like ),
                new Data("Dislikes : "+ dis,dis));

// Créer le graphique à secteurs
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Likes vs Dislikes");

// Afficher le graphique dans une fenêtre
        StackPane root = new StackPane(pieChart);
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

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
