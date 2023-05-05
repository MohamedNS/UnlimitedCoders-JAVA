/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.GUI.Client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author L390
 */
public class ListeArticleFront implements Initializable {

    @FXML
    private VBox content;
    @FXML
    private TextField testReportSearchTv;
    @FXML
    private Label searchResultNumber;
    @FXML
    private ListView<?> testReportListView;
    @FXML
    private Button backID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSearchBtnClick(ActionEvent event) {
    }

    @FXML
    private void BackToMainPage(ActionEvent event) {
    }
    
}
