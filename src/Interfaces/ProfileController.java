/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Services.ServiceUser;
import Services.UserSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shayma
 */
public class ProfileController implements Initializable {

    @FXML
    private Circle imageView;
    @FXML
    private Label nomdash;
    @FXML
    private Label prenomdash;
    
               public List<File> findAllFilesInFolder(File folder) {
        List<File> list = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                list.add(file);

            } else {
                findAllFilesInFolder(file);
            }
        }
        return list;
    }

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button home_btn;
    @FXML
    private Label logout;
    @FXML
    private AnchorPane home_form;
    @FXML
    private Label nom;
    
    File file; 
    
    @FXML
    private Label telephone;
    @FXML
    private Label prenom;
    @FXML
    private Label email;
    @FXML
    private Button gererprofile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          UserSession userSession = new UserSession();
          ServiceUser userService = new ServiceUser();
//            File folder = new File("C:\\xampp\\htdocs\\uploads\\images");
//        Circle cir2 = new Circle(250, 250, 100);
//        for (int i = 0; i < findAllFilesInFolder(folder).size(); i++) {
//            if (findAllFilesInFolder(folder).get(i).getName().equals(userSession.getUser().getImage())) {
//                try {
//                    Image imge = new Image(new FileInputStream("C:\\xampp\\htdocs\\uploads\\images\\" + userSession.getUser().getImage()));
//                    imageView.setFill(new ImagePattern(imge));
//                    cir2.setFill(new ImagePattern(imge));
//                } catch (FileNotFoundException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        }
          
        nom.setText(userSession.getUser().getNom());
        nomdash.setText(userSession.getUser().getNom());
        prenom.setText(userSession.getUser().getPrenom());
        prenomdash.setText(userSession.getUser().getPrenom());
        email.setText(userSession.getUser().getEmail()); 
        telephone.setText(userSession.getUser().getTelephone()); 
        
       
    }    


    

    @FXML
    private void logout(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    @FXML
    private void gererprofile(MouseEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("update.fxml"));
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }








    
}
