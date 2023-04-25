/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Entities.User;
import Services.ServiceUser;
import Services.UserSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author shayma
 */
public class UpdateController implements Initializable {
    
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
    private AnchorPane home_form;
    @FXML
    private Button update;
    @FXML
    private TextField nom;
    @FXML
    private TextField telephone;
    @FXML
    private TextField prenom;
    
    File file; 
    
    @FXML
    private Circle imageView;
    @FXML
    private Button saveimage;
    @FXML
    private Button image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              UserSession userSession = new UserSession();
          ServiceUser userService = new ServiceUser();
        nom.setText(userSession.getUser().getNom());
        prenom.setText(userSession.getUser().getPrenom());
        telephone.setText(userSession.getUser().getTelephone()); 
    }    

    @FXML
    private void close(ActionEvent event) {
    }

    @FXML
    private void minimize(ActionEvent event) {
    }

    @FXML
    private void update(MouseEvent event) throws IOException {
        ServiceUser user1 = new ServiceUser();
        UserSession session = new UserSession();
        String emailS = session.getUser().getEmail();

        String name = nom.getText();
        String lastName = prenom.getText();
         String num = telephone.getText();

         User user = new User(name, lastName,num );
         user1.Update(user, emailS);
         System.out.println(user);
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    private void saveimage(MouseEvent event) throws FileNotFoundException, IOException {
//
//        
//            ServiceUser u = new ServiceUser();
//        UserSession session = new UserSession();
//        String emailS = session.getUser().getEmail();
//
//               FileInputStream fl = new FileInputStream(file);
//                byte[] data = new byte[(int) file.length()];
//                String fileName = file.getName();
//                fl.read(data);
//                fl.close();
//
//         User user = new User(fileName );
//         u.addImage(user, emailS);
//    
//              Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//     Scene scene = new Scene(root);
//     stage.setScene(scene);
//     stage.show();
//    }
//
//    @FXML
//private void image(MouseEvent event) {
//                Path to = null;
//        String m = null;
//        String path = "C:\\xampp\\htdocs\\uploads\\images";
//        JFileChooser chooser = new JFileChooser();
//
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "JPG & PNG Images", "jpg", "jpeg", "PNG");
//        chooser.setFileFilter(filter);
//        int returnVal = chooser.showOpenDialog(null);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            m = chooser.getSelectedFile().getAbsolutePath();
//
//            file = chooser.getSelectedFile();
//            String fileName = file.getName();
//
//            if (chooser.getSelectedFile() != null) {
//
//                try {
//                    Path from = Paths.get(chooser.getSelectedFile().toURI());
//                    to = Paths.get(path + "\\" + fileName);
//                    
//                    CopyOption[] options = new CopyOption[]
//                    {
//                        StandardCopyOption.REPLACE_EXISTING,
//                        StandardCopyOption.COPY_ATTRIBUTES
//                    };
// 
//                    Files.copy(from, to, options);
//                    System.out.println("added");
// 
//                } catch (IOException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//
//        }
//    }
    
    
    
}
