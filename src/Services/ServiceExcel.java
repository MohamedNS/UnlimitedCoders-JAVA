/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;

import Entity.Consultation;
import com.lowagie.tools.plugins.Concat;
import com.mysql.cj.exceptions.MysqlErrorNumbers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bytesudoer
 */
public class ServiceExcel {
	private static final String delimiter = ",";
	private static final String separator = "\n";

	public ServiceExcel() {
	}
	
	public void generateExcelConsultation(List<Consultation> liste,String filename)
	{
		String header = "Matricule Medecin,Id Patient,Date Consultation,Montant";
		try{
			FileWriter file = new FileWriter(filename+".csv");
			file.append(header);
			file.append(separator);
			for(Consultation c: liste)
			{
				file.append(c.getMatriculeMedecin());
				file.append(delimiter);
				file.append(c.getIdPatient());
				file.append(delimiter);
				file.append(String.valueOf(c.getDateConsultation()));
				file.append(delimiter);
				file.append(String.valueOf(c.getMontant()));
				file.append(separator);
			}
			file.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public List<Consultation> importerExcelConsultation(File file) throws FileNotFoundException
	{
		List<Consultation> listeConsultation = new ArrayList<>();
		BufferedReader bufreder = new BufferedReader(new FileReader(file));
		String lineText = null;
		try{
			bufreder.readLine();
			while((lineText = bufreder.readLine()) != null)
			{
				Consultation c = new Consultation();
				String[] data = lineText.split(",");
				c.setMatriculeMedecin(data[0]);
				c.setIdPatient(data[1]);
				c.setDateConsultation(java.sql.Date.valueOf(data[2]));
				c.setMontant(Float.parseFloat(data[3]));
				listeConsultation.add(c);
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return listeConsultation;
		
	}

}
