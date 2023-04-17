/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Consultation;
import Utils.MyConnection;
import interfaces.InterfaceConsultation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bytesudoer
 */
public class ServiceConsultation implements InterfaceConsultation{

    Connection cnx;

    public ServiceConsultation() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public List<Consultation> afficherConsultation() {
        List<Consultation> consultations = new ArrayList<>();
        try {
            String req = "SELECT * from consultation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Consultation c = new Consultation();
                c.setId(rs.getInt("id"));
                c.setMatriculeMedecin(rs.getString("matriculemedecin"));
                c.setIdPatient(rs.getString("idpatient"));
                c.setDateConsultation(rs.getDate("dateconsultation"));
                c.setMontant(rs.getFloat("montant"));

                consultations.add(c);
            }
            //System.out.print("Consultation : " + consultations);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return consultations;
    }

    @Override
    public void ajouterConsultation(Consultation c) {
        try {
            PreparedStatement st = cnx.prepareStatement("INSERT into consultation"
                    + "(matriculemedecin,idpatient,dateconsultation,montant)"
                    + "values(?,?,?,?)");
            st.setString(1, c.getMatriculeMedecin());
            st.setString(2, c.getIdPatient());
            st.setDate(3, (Date) c.getDateConsultation());
            st.setFloat(4, c.getMontant());
            st.executeUpdate();
            System.out.print("Consultation Ajoutee");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifierConsultation(Consultation c) {
        try {
            String req = "update consultation set matriculemedecin=?,idpatient=?,"
                    + "dateconsultation=?,montant=? where id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, c.getMatriculeMedecin());
            ps.setString(2, c.getIdPatient());
            ps.setDate(3, (Date) c.getDateConsultation());
            ps.setFloat(4, c.getMontant());
            ps.setInt(5, c.getId());
            System.out.println("Modifier SQL : " + ps.toString());
            ps.executeUpdate();
            System.out.println("Consultation Modifiee");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }

    }

    @Override
    public void supprimerConsultation(int id) {
        try {
            String req = "Delete from consultation where id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Consultation Supprimee");

        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }

	@Override
	public Consultation trouverParId(int id)
	{
		Consultation c = new Consultation();
		try{
			String req = "SELECT * from consultation where id="+id;
			Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery(req);
			while(rs.next())
			{
				c.setId(rs.getInt("id"));
				c.setMatriculeMedecin(rs.getString("matriculemedecin"));
				c.setIdPatient(rs.getString("idpatient"));
				c.setDateConsultation(rs.getDate("dateconsultation"));
				c.setMontant(rs.getFloat("montant"));
			}
		}
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return c;
		
	}

    @Override
    public List<Consultation> trierConsultations(String str1, String str2) {
        List<Consultation> listeConsultations = new ArrayList<>();
        String critere = convertirCritre(str1);
        String ordre = convertirOrdre(str2);
        try{
            String req = "Select * from consultation order by "+critere+" "+ordre;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                Consultation c = new Consultation();
                c.setId(rs.getInt("id"));
                c.setMatriculeMedecin(rs.getString("matriculemedecin"));
                c.setIdPatient(rs.getString("idpatient"));
                c.setDateConsultation(rs.getDate("dateconsultation"));
                c.setMontant(rs.getFloat("montant"));
                listeConsultations.add(c);
            }
            
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return listeConsultations;
    }

    @Override
    public String convertirCritre(String str) {
        String val = "";
        switch (str) {
            case "Identifiant":
                val = "id";
                break;
            case "Matricule Medecin":
                val = "matriculemedecin";
                break;
            case "Id Patient":
                val = "idpatient";
                break;
            case "Date Consultation":
                val = "dateconsultation";
                break;
            case "Montant":
                val = "montant";
                break;
            default:
                throw new AssertionError();
        }
        return val;
    }

    @Override
    public String convertirOrdre(String str) {
        String val = "";
        switch (str) {
            case "Croissant":
                val = "asc";
                break;
            case "Decroissant":
                val = "desc";
                break;
            default:
                throw new AssertionError();
        }
        return val;
    }

}
