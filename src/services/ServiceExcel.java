/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Facteur;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public ServiceExcel() {
    }
    public void genererxlsxFacteur(String filename,List<Facteur> listeFacteur) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
		
		HSSFWorkbook table = new HSSFWorkbook ();
                HSSFSheet sheet = table.createSheet(filename+".xlsx");
                HSSFRow header =sheet.createRow(0);
        System.out.println("Generation facteur xlsx");
      
	
		header.createCell(0).setCellValue("Identifiant");
		header.createCell(1).setCellValue("cin");
		header.createCell(2).setCellValue("nom");
		header.createCell(3).setCellValue("prenom");
		header.createCell(4).setCellValue("id_patient");
                header.createCell(5).setCellValue("id_medicament");
                header.createCell(6).setCellValue("nom_med");
                header.createCell(7).setCellValue("dosage");
                header.createCell(8).setCellValue("prix");
                int index =1;
	    for(Facteur consultation:listeFacteur)
        {
                        HSSFRow row =sheet.createRow(index);
			row.createCell(0).setCellValue(String.valueOf(consultation.getId()));
			row.createCell(1).setCellValue(consultation.getCin());
			row.createCell(2).setCellValue(consultation.getNom());
			row.createCell(3).setCellValue(consultation.getPrenom());
			row.createCell(4).setCellValue(String.valueOf(consultation.getId_patient()));
                        row.createCell(5).setCellValue(String.valueOf(consultation.getId_medicament()));
                        row.createCell(6).setCellValue(consultation.getNom_med());
                        row.createCell(7).setCellValue(consultation.getDosage());
                        row.createCell(8).setCellValue(String.valueOf(consultation.getPrix()));
                        index++;
        }
		FileOutputStream filOut = new FileOutputStream(filename+".xlsx");
                table.write(filOut);
                filOut.close();

       
    }
}
