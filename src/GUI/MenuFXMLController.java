/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class MenuFXMLController implements Initializable {
    
    @FXML
    private Button btnAssurance;

    @FXML
    private Button btnDepot;
    
    @FXML
    private Button btnRemboursement;
    
    @FXML
    private Button btnRetour;
    
    @FXML
    private RadioButton btnStat;

 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    @FXML
    public void btnAssurance(ActionEvent evt)
    {
        try{
            System.out.println("Assurance Click");
            Parent loader = FXMLLoader.load(getClass().getResource("AssuranceFXML.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnAssurance.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void btnDepot(ActionEvent evt)
    {
        try
        {
            System.out.println("Depot Click");
            Parent loader = FXMLLoader.load(getClass().getResource("DepotFXML.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnDepot.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void btnRemboursement(ActionEvent evt)
    {
        try{
            System.out.println("Remboursement Click");
            Parent loader = FXMLLoader.load(getClass().getResource("RemboursementFXML.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnRemboursement.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void show()
    {
        try{
            
            System.out.println("Gestion Assurance Click");
            Parent loader = FXMLLoader.load(getClass().getResource("MenuFXML.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }
    
    @FXML
    public void btnStat(ActionEvent evt)
    {
        try{
            System.out.println("Statistique Click");
            Parent loader = FXMLLoader.load(getClass().getResource("ChartsFXML.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = (Stage) btnStat.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void btnRetour(ActionEvent evt) throws IOException
    {
         Parent mainPageParent = FXMLLoader.load(getClass().getResource("/GUI/BienvenueAdmin.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);

        //Get the current window and set the scene to the main page scene
        Stage window = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
        
        
    }
    
    
}
