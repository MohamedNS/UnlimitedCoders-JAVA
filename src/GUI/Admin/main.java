package GUI.Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/Esprit/MainGui/mainback/main.fxml"));
        //  Parent root = FXMLLoader.load(getClass().getResource("/Esprit/MainGui/Admin/ListArticle.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Admin/PiChartView.fxml"));
        //  URL url = new File("/Esprit/MainGui/Admin/ListArticle.fxml").toURI().toURL();
        // Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("Article");
        Scene scene = new Scene(root, 1200, 857); // ratio is 1.4:1

        scene.getStylesheets().addAll(getClass().getResource("/resources/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
