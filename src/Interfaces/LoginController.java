/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Entity.Role;
import Entity.User;
import Services.ServiceUser;
import Services.UserSession;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shayma
 */
public class LoginController implements Initializable {

    @FXML
    private TextField email;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private Button register;
    @FXML
    private Label frgpass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void register(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void login(MouseEvent event) throws IOException {
        if (event.getSource() == loginBtn) {
            if (validateEmail(email) & validatePassword(password)) {
                String roles = null;
                User user = ServiceUser.login(email.getText(), password.getText());
                ServiceUser.userSession = new UserSession();
                ServiceUser.userSession.setUserEmail(user.getEmail());

                System.out.println(user.getRoles());
                System.out.println(user.getTelephone());
                System.out.println(user.getIsbanned());
                if ("[\"ROLE_USER\"]".equals(user.getRoles())) {

                    if (1 == user.getIsbanned()) {
                        Parent root2 = FXMLLoader.load(getClass().getResource("../Interfaces/banned.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root2);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        Parent root2 = FXMLLoader.load(getClass().getResource("../Interfaces/profile.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root2);
                        stage.setScene(scene);
                        stage.show();
                    }

                } else if ("[\"ROLE_ADMIN\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../Interfaces/AdminHome.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if ("[\"ROLE_Produit\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/home.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if ("[\"ROLE_RDV\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/FirstWindowRDV.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if ("[\"ROLE_Assurance\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/MenuFXML.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if ("[\"ROLE_Pharmacien\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/FXML.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if ("[\"ROLE_Consultation\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/Menu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if ("[\"ROLE_Article\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/Admin/AdminMain.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
				else if ("[\"ROLE_Medecin\"]".equals(user.getRoles())) {
                    Parent root = FXMLLoader.load(getClass().getResource("../GUI/MenuBienvenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    @FXML
    private void frgpass(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("forgetpass.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
