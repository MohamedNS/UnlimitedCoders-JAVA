/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Consultation;
import Entity.Medicament;
import Entity.Ordonnance;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bytesudoer
 */
public class ServicePDF {

    public ServicePDF() {
    }
    public void genererPdfConsultation(String filename,List<Consultation> listeConsultations) throws FileNotFoundException, DocumentException
    {
        System.out.println("Generation Consultation PDF");
        Document document = new Document(){
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();
        document.add(new Paragraph("             Date : "+LocalDate.now()));
        for(Consultation c:listeConsultations)
        {
            document.add(new Paragraph("---------------------------------------------------------------"));
            document.add(new Paragraph("       Consultation : "+c.getId()));
            document.add(new Paragraph("       Matricule Medecin : "+c.getMatriculeMedecin()));
            document.add(new Paragraph("       Identifiant Patient : "+c.getIdPatient()));
            document.add(new Paragraph("       Date Consultation: "+c.getDateConsultation()));
            document.add(new Paragraph("       Montant : "+c.getMontant()+" TND"));
            document.add(new Paragraph("---------------------------------------------------------------"));
        }
        document.close();
    }
    public void genererPdfOrdonnance(String filename,List<Ordonnance> listeOrdonnances) throws FileNotFoundException, DocumentException
    {
        System.out.println("Generation Ordonnance PDF");
        Document document = new Document(){
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();
        document.add(new Paragraph("             Date : "+LocalDate.now()));
        for(Ordonnance o: listeOrdonnances)
        {
            document.add(new Paragraph("---------------------------------------------------------------"));
            document.add(new Paragraph("          Ordonnance : "+o.getId()));
            document.add(new Paragraph("          Identifiant Consultation : "+o.getConsultation_id()));
            document.add(new Paragraph("          Validite : "+o.getValidite()+" Jours"));
            document.add(new Paragraph("          Liste Medicaments : "+o.getNomMedicaments()));
            document.add(new Paragraph("---------------------------------------------------------------"));
        }
        document.close();
    }
    public void genererPdfMedicament(String filename,List<Medicament> listeMedicaments) throws FileNotFoundException, DocumentException
    {
        System.out.println("Generation Medicament PDF");
        Document document = new Document(){
        };
        PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
        document.open();
        document.add(new Paragraph("             Date : "+LocalDate.now()));
        for(Medicament m : listeMedicaments)
        {
            document.add(new Paragraph("---------------------------------------------------------------"));
            document.add(new Paragraph("Medicament : "+m.getId()));
            document.add(new Paragraph("Nom : "+m.getNom()));
            document.add(new Paragraph("Dosage : "+m.getDosage()+" xFois/Jour"));
            document.add(new Paragraph("Prix : "+m.getPrix()+" TND"));
            document.add(new Paragraph("Description : "+m.getDescription()));
            document.add(new Paragraph("---------------------------------------------------------------"));
        }
        document.close();
    }
    
}
