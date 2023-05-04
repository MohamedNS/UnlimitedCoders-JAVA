/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Services;

import Entity.MedicamentN;
import Utils.MyConnection;
import interfaces.InterfaceMedicament;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bytesudoer
 */
public class ServicesMedicamentN implements InterfaceMedicament {
Connection cnx;

    public ServicesMedicamentN() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public List<MedicamentN> afficherMedicament() {
        List<MedicamentN> medicaments = new ArrayList<>();

        try {
            String req = "select * from medicamentN";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                MedicamentN m = new MedicamentN();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setDosage(rs.getInt("dosage"));
                m.setPrix(rs.getFloat("prix"));
                m.setDescription(rs.getString("description"));

                medicaments.add(m);
            }
            //System.out.println(medicaments);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return medicaments;
    }

    @Override
    public void ajouterMedicament(MedicamentN m) {
        try {
            PreparedStatement st = cnx.prepareStatement("INSERT into medicamentN"
                    + "(nom,dosage,prix,description)"
                    + "values(?,?,?,?)");
            st.setString(1, m.getNom());
            st.setInt(2, m.getDosage());
            st.setFloat(3, m.getPrix());
            st.setString(4, m.getDescription());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifierMedicament(MedicamentN m) {
        try {
            String req = "UPDATE medicamentN set nom=?,"
                    + "dosage=?,prix=?,"
                    + "description=? where id=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, m.getNom());
            st.setInt(2, m.getDosage());
            st.setFloat(3, m.getPrix());
            st.setString(4, m.getDescription());
            st.setInt(5, m.getId());
            st.executeUpdate();
            System.out.println("Medicament Modifie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerMedicament(int id) {
        try {
            String req = "DELETE from medicamentN where id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Medicament Supprime");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public MedicamentN trouverParId(int id)
    {
        MedicamentN m = new MedicamentN();
        try{
            String req = "SELECT * from medicamentN where id="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                
            m.setId(id);
            m.setNom(rs.getString("nom"));
            m.setDosage(rs.getInt("dosage"));
            m.setPrix(rs.getFloat("prix"));
            m.setDescription(rs.getString("description"));
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return m;
    }
	@Override
	public MedicamentN trouverParNom(String nom)
	{
		MedicamentN m = new MedicamentN();
		try {
			String req = "SELECT * from medicamentN where nom=?";
			PreparedStatement pt = cnx.prepareStatement(req);
			pt.setString(1, nom);
			ResultSet rs = pt.executeQuery();
			
			while(rs.next())
			{
				m.setId(rs.getInt("id"));
				m.setNom(nom);
				m.setDosage(rs.getInt("dosage"));
				m.setPrix(rs.getFloat("prix"));
				m.setDescription(rs.getString("description"));
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return m;
	}
	@Override
	public float calculerMoyennePrix()
	{
		float val = 0;
		try {
			String req = "Select avg(prix) from medicamentN";
			Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery(req);
			while(rs.next())
			{
				val = rs.getFloat(1);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return val;
	}

    @Override
    public List<MedicamentN> trierMedicament(String str1, String str2) {
        List<MedicamentN> listeMedicaments = new ArrayList<>();
        String critere = convertiCritere(str1);
        String ordre = convertirOrdre(str2);
        
        try{
            String req = "Select * from medicamentN order by "+critere+" "+ordre;
            Statement st =cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                MedicamentN m = new MedicamentN();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setDosage(rs.getInt("dosage"));
                m.setPrix(rs.getFloat("prix"));
                m.setDescription(rs.getString("description"));
                listeMedicaments.add(m);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return listeMedicaments;
    }

    @Override
    public String convertiCritere(String str) {
        String val = "";
        switch (str) {
            case "Identifiant":
                val = "id";
                break;
            case "Nom":
                val = "nom";
                break;
            case "Dosage":
                val = "dosage";
                break;
            case "Prix":
                val = "prix";
                break;
            default:
                throw new AssertionError();
        }
        return val;
    }

    @Override
    public String convertirOrdre(String ordre) {
        String val = "";

        switch (ordre) {
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
