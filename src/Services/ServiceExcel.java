/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import Entity.Facteur;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author MSI
 */
public class ServiceExcel {
    private static final String delimiter = ",";
	private static final String separator = "\n";
    public ServiceExcel() {
    }
    public void genererxlsxFacteur(String filename,List<Facteur> listeFacteur) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
		String header = "Identifiant,cin,nom,prenom,id_patient,d_medicament,nom_med,dosage,prix";
		FileWriter file = new FileWriter(filename+".csv");
		file.append(header);
			file.append(separator);
        System.out.println("Generation facteur xlsx");
      
	
		
                int index =1;
	    for(Facteur consultation:listeFacteur)
        {
                        
			file.append(String.valueOf(consultation.getId()));
                        file.append(delimiter);
			file.append(consultation.getCin());
                        file.append(delimiter);
			file.append(consultation.getNom());
			file.append(delimiter);
                        file.append(consultation.getPrenom());
                        file.append(delimiter);
			file.append(String.valueOf(consultation.getId_patient()));
                        file.append(delimiter);
                        file.append(String.valueOf(consultation.getId_medicament()));
                        file.append(delimiter);
                        file.append(consultation.getNom_med());
                        file.append(delimiter);
                        file.append(consultation.getDosage());
                        file.append(delimiter);
                       file.append(String.valueOf(consultation.getPrix()));
                       file.append(delimiter);
                        index++;
        }
		
                file.close();

       
    }
}
