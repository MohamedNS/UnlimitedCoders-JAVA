/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

/**
 *
 * @author MSI
 */
import Entity.Consultation;
import Entity.Medicament;
import Entity.Ordonnance;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import Entity.Facteur;
import com.itextpdf.text.Element;
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
public class ServicePDF {

    public ServicePDF() {
    }
    public void genererPdfFacteur(String filename,List<Facteur> listeFacteur) throws FileNotFoundException, DocumentException, BadElementException, IOException
    {
		Image image = Image.getInstance("src/images/LogoApp.png");
		image.setAlignment(Element.ALIGN_CENTER);
		float pageWidth = PageSize.A4.getWidth();
		float pageHeight = PageSize.A4.getHeight();
		float imageWidth = image.getScaledWidth();
		float x = (pageWidth - imageWidth)/2;
		float y = pageHeight - image.getScaledHeight();
		PdfPTable table = new PdfPTable(5);
        System.out.println("Generation facteur PDF");
        Document document = new Document(){
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();	
		document.add(new Paragraph("                              Liste des Facteur                                    "));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
                document.add(new Paragraph(""));
                document.add(new Paragraph(""));
                document.add(new Paragraph(""));
                document.add(new Paragraph(""));
                document.add(new Paragraph(""));
		document.add(image);
		table.addCell("Identifiant");
		table.addCell("cin");
		table.addCell("nom");
		table.addCell("prenom");
		table.addCell("id_patient");
                table.addCell("id_medicament");
                table.addCell("nom_med");
                table.addCell("dosage");
                table.addCell("prix");
	    for(Facteur consultation:listeFacteur)
        {
			table.addCell(String.valueOf(consultation.getId()));
			table.addCell(consultation.getCin());
			table.addCell(consultation.getNom());
			table.addCell(consultation.getPrenom());
			table.addCell(String.valueOf(consultation.getId_patient()));
                        table.addCell(String.valueOf(consultation.getId_medicament()));
                        table.addCell(consultation.getNom_med());
                        table.addCell(consultation.getDosage());
                        table.addCell(String.valueOf(consultation.getPrix()));
        }
		document.add(table);

        document.add(new Paragraph("             Date : "+LocalDate.now()));
        document.close();
    }
    public void genererPdfOrdonnance(String filename,List<Ordonnance> listeOrdonnances) throws FileNotFoundException, DocumentException, IOException, BadElementException
    {Image image = Image.getInstance("src/images/LogoApp.png");
			image.setAlignment(Element.ALIGN_CENTER);
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
			for(Ordonnance o : listeOrdonnances)
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
    public void genererPdfMedicament(String filename,List<Medicament> listeMedicaments) throws FileNotFoundException, DocumentException, BadElementException, IOException
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
		for(Medicament m : listeMedicaments)
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
