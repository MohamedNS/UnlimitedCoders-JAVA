/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaces;

import Services.ServiceUser;
import com.twilio.Twilio;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author shayma
 */
public class ForgetpassController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static final String ACCOUNT_SID = "AC02db107c7ec6a46914416263860ec7ce";
    public static final String AUTH_TOKEN = "3f215976ca3aeb69e0d0077f75b44b12";
    
    ServiceUser serviceUser = new ServiceUser();
    public String y, z;
    public String username, pass, mesg;

    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField numero;
    @FXML
    private Button sendSms;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    protected String getToken() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @FXML
    private void sendSms(MouseEvent event) throws SQLException, IOException {
        ServiceUser u = new ServiceUser();
        serviceUser.sendSMS(numero.getText());
        y = getToken();
        z ="+216"+numero.getText();
        try {
            String user = serviceUser.sendSMS(numero.getText());
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        mesg = "Your Healthified Reset Code is : " + y;

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber(serviceUser.sendSMS(z)),
                new PhoneNumber("+16205249916"), y).create();
        //  User userSetToken = new User(y, true);           
        u.setToken(y, numero.getText());
        // System.out.println(y);
        root = FXMLLoader.load(getClass().getResource("verificationCode.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
