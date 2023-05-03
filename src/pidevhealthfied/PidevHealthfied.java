/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pidevhealthfied;

import Utils.MyConnection;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class PidevHealthfied extends Application {

    @Override
    public void start(Stage primaryStage)
    {
		Parent root;
     try
     {
        
         root =FXMLLoader.load(getClass().getResource("../Interfaces/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
     }
     catch(IOException ex )
     {
         System.out.println(ex.getMessage());
     }
    }
    public static void main(String[] args)
    {launch(args);}
    
}
