/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Consultation;
import Entity.Medicament;
import Entity.MedicamentN;
import Entity.OrdonnanceN;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bytesudoer
 */
public class ServicePDFN {

    public ServicePDFN() {
    }
    public void genererPdfConsultation(String filename,List<Consultation> listeConsultations) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
		Image image = Image.getInstance("src/images/LogoApp.png");
		float pageWidth = PageSize.A4.getWidth();
		float pageHeight = PageSize.A4.getHeight();
		float imageWidth = image.getScaledWidth();
		float x = (pageWidth - imageWidth)/2;
		float y = pageHeight - image.getScaledHeight();
		image.setAbsolutePosition(x,y);
		image.scaleAbsolute(imageWidth,image.getScaledHeight());
		PdfPTable table = new PdfPTable(5);
        System.out.println("Generation Consultation PDF");
        Document document = new Document(){
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();	
		document.add(new Paragraph("                              Liste des Consultations                                     "));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		document.add(image);
		table.addCell("Identifiant");
		table.addCell("Matricule Medecin");
		table.addCell("Identifiant Patient");
		table.addCell("Date Consultation");
		table.addCell("Montant");
	    for(Consultation consultation:listeConsultations)
        {
			table.addCell(String.valueOf(consultation.getId()));
			table.addCell(consultation.getMatriculeMedecin());
			table.addCell(consultation.getIdPatient());
			table.addCell(consultation.getDateConsultation().toString());
			table.addCell(String.valueOf(consultation.getMontant()));
        }
		document.add(table);

        document.add(new Paragraph("             Date : "+LocalDate.now()));
        document.close();
    }
    public void genererPdfOrdonnance(String filename,List<OrdonnanceN> listeOrdonnances) throws FileNotFoundException, DocumentException, IOException, BadElementException
    {
			Image image = Image.getInstance("src/images/LogoApp.png");
			float pageWidth = PageSize.A4.getWidth();
			float pageHeight = PageSize.A4.getHeight();
			float imageWidth = image.getScaledWidth();
			float x = (pageWidth - imageWidth)/2;
			float y = pageHeight - image.getScaledHeight();
			image.setAbsolutePosition(x,y);
			image.scaleAbsolute(imageWidth,image.getScaledHeight());
			PdfPTable table = new PdfPTable(4);
			System.out.println("Generation Ordonnance PDF");
			Document document = new Document(){
			};
			PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
			document.open();
			document.add(new Paragraph("                              Liste des Ordonnances                                     "));
			document.add(image);
			table.addCell("Identifiant");
			table.addCell("Identifiant Consultation");
			table.addCell("Validite");
			table.addCell("Liste Medicaments");
			for(OrdonnanceN o : listeOrdonnances)
			{
				table.addCell(String.valueOf(o.getId()));
				table.addCell(String.valueOf(o.getConsultation_id()));
				table.addCell(String.valueOf(o.getValidite()));
				table.addCell(o.getNomMedicaments());
			}
			document.add(table);
			document.add(new Paragraph("             Date : "+LocalDate.now()));
			
			document.close();
    }
    public void genererPdfMedicament(String filename,List<MedicamentN> listeMedicaments) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
		Image image = Image.getInstance("src/images/LogoApp.png");
		float pageWidth = PageSize.A4.getWidth();
		float pageHeight = PageSize.A4.getHeight();
		float imageWidth = image.getScaledWidth();
		float x = (pageWidth - imageWidth)/2;
		float y = pageHeight - image.getScaledHeight();
		image.setAbsolutePosition(x,y);
		image.scaleAbsolute(imageWidth,image.getScaledHeight());
		PdfPTable table = new PdfPTable(5);
        System.out.println("Generation Medicament PDF");
        Document document = new Document(){
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();
		document.add(new Paragraph("                              Liste des Medicaments                                     "));
		document.add(image);
		table.addCell("Identifiant");
		table.addCell("Nom");
		table.addCell("Dosage");
		table.addCell("Prix");
		table.addCell("Description");
		for(MedicamentN m : listeMedicaments)
        {
			table.addCell(String.valueOf(m.getId()));
			table.addCell(m.getNom());
			table.addCell(String.valueOf(m.getDosage()));
			table.addCell(String.valueOf(m.getPrix()));
			table.addCell(m.getDescription());
		}
		document.add(table);
        document.add(new Paragraph("             Date : "+LocalDate.now()));
        document.close();
    
}

}
