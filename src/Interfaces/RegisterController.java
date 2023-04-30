/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Entities.Role;
import Entities.User;
import Services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.mail.MessagingException;


/**
 * FXML Controller class
 *
 * @author shayma
 */
public class RegisterController implements Initializable {
ServiceUser userService = new ServiceUser();
    @FXML
    private AnchorPane main_form;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private Button register;
    @FXML
    private TextField prenom;
    @FXML
    private TextField nom;
    @FXML
    private TextField telephone;
    @FXML
    private Button login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void register(MouseEvent event) throws IOException, MessagingException {
                if (validateString(nom) & validateString(prenom) & validateEmail(email)
                & validatePassword(password)) {
     
        
             Window owner = register.getScene().getWindow();                
             User user = new User(nom.getText(), prenom.getText(), telephone.getText(), email.getText(), password.getText(), true);           
                userService.register(user);
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();     
    }
    }
    
            private boolean validateString(TextField string) {
        Pattern p = Pattern.compile("[a-zA-Z0-9_]+");
        Matcher m = p.matcher(string.getText());
        if ((string.getText().length() == 0)
                || (!m.find() && m.group().equals(string.getText()))) {
            new animatefx.animation.Shake(string).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            string.setEffect(in);
            return false;
        } else {
            string.setEffect(null);
            return true;
        }
    }
            private boolean validateEmail(TextField email) {

        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(email.getText());
        if (((email.getText().length() > 8))
                && (m.find() && m.group().equals(email.getText()))) {
            email.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(email).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            email.setEffect(in);
            return false;
        }

    }
             private boolean validatePassword(TextField password) {

        Pattern p = Pattern.compile("[a-zA-Z_0-9]+");
        Matcher m = p.matcher(password.getText());
        if (((password.getText().length() > 6))
                && (m.find() && m.group().equals(password.getText()))) {
            password.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(password).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            password.setEffect(in);
            return false;
        }

    }

    @FXML
    private void login(MouseEvent event) throws IOException {  Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    
}
