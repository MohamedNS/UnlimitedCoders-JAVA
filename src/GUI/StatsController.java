/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entity.FicheAssurance;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import Services.FicheAssuranceGrud;
import Utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class StatsController implements Initializable {

    @FXML
    private PieChart plusutilisePi;
   
Connection cnx;
ObservableList<FicheAssurance> List = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     * 
     */
    
    public void remplirePiChart()
	{
		cnx = MyConnection.getInstance().getConnection();
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		try {
			String req = "SELECT COUNT(*) AS count, fiche_assurance.matricule_cnam FROM fiche_assurance GROUP BY fiche_assurance.matricule_cnam ";
PreparedStatement pt = cnx.prepareStatement(req);

ResultSet rs = pt.executeQuery();
System.out.println("cnam est "+req);

			while(rs.next())
			{
				data.add(new PieChart.Data(rs.getString("matricule_cnam")+ " x" +rs.getInt("count"), rs.getInt("count")));
			}
			plusutilisePi.setTitle("fiche_assurance par assurance");
			plusutilisePi.setData(data);

	    } catch (SQLException ex) {
			System.out.println(ex.getMessage());
	    }
	}
	
	public void afficherStatistiques()
	{
		FicheAssuranceGrud sv = new FicheAssuranceGrud();
		//String moyenne = String.format("%.2f", sv.calculerMoyennePrix());
		//moyennePrixLabel.setText(moyenne+" TND");
		this.remplirePiChart();
		
	}
    public void initialize(URL url, ResourceBundle rb) {
        
        afficherStatistiques();
        // TODO
    }    
      @FXML
    private void btnnext(ActionEvent event) throws IOException {
         Parent mainPageParent = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene mainPageScene = new Scene(mainPageParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.show();
    }
    
}
