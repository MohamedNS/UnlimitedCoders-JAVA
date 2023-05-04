/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class ApplicationHealthified extends Application {

    @Override
    public void start(Stage stage){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            
            stage.setTitle("Menu Healthified");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
public static void main(String[] args) {

        launch(args);
        
        /*ServiceConsultation sv_consultation = new ServiceConsultation();
        ServiceOrdonnance sv_ordonnance = new ServiceOrdonnance();
        ServiceMedicament sv_med = new ServiceMedicament();
        sv_consultation.afficherConsultation();
        System.out.println("\n");
        sv_ordonnance.afficherOrdonnance();
        System.out.println("\n");
        sv_med.afficherMedicament();*/
    }
    
}
