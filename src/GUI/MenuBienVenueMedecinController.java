/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package GUI;

import com.itextpdf.text.pdf.PdfName;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author bytesudoer
 */
public class MenuBienVenueMedecinController implements  Initializable{

	@FXML
	private Button btnRetour;
	@FXML
	private Button btnRdv;
	@FXML
	private Button btnConsultation;
	@FXML
	private Button btnProduit;

	@FXML
	public void btnRetour(ActionEvent evt)
	{
		Stage stage = (Stage) btnRetour.getScene().getWindow();
		stage.close();
		
		
	}
	@FXML
	public void btnRdv(ActionEvent evt) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../GUI/Medecin.fxml"));
		try{
			loader.load();
			Parent root = loader.getRoot();
			Stage stage = (Stage) btnRdv.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
		catch(IOException ex)
		{
			System.err.println(ex.getMessage());
		}
	}
	@FXML
	public void btnConsultation(ActionEvent evt)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../GUI/Menu.fxml"));
		try{
			loader.load();
			Parent root = loader.getRoot();
			Stage stage = (Stage) btnRdv.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
		catch(IOException ex)
		{
			System.err.println(ex.getMessage());
		}
		
	}
	@FXML
	public void btnProduit(ActionEvent evt)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../GUI/home.fxml"));
		try{
			loader.load();
			Parent root = loader.getRoot();
			Stage stage = (Stage) btnRdv.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			}
		catch(IOException ex)
		{
			System.err.println(ex.getMessage());
		}

		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
