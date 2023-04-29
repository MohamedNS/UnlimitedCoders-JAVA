
package bankin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ConnectionDB.MyConnection;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.scene.Scene;

public class Bankin extends Application
{
    @Override
    public void start(Stage primaryStage)
    {

     try
     {
        MyConnection.getInstance();
        Parent root =FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Interfaces/login.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
     }
     catch(IOException ex )
     {
         Logger.getLogger(Bankin.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    public static void main(String[] args)
    {launch(args);}

}