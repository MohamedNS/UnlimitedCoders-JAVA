/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;
import Entity.Consultation;
import Entity.Medicament;
import Utils.MyConnection;
import interfaces.InterfaceMedicament;
import java.sql.*;
import java.util.*;


/**
 *
 * @author bytesudoer
 */
public class ServiceMedicament implements InterfaceMedicament{

    Connection cnx;

    public ServiceMedicament() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public List<Medicament> afficherMedicament() {
        List<Medicament> medicaments = new ArrayList<>();

        try {
            String req = "select * from medicament";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Medicament m = new Medicament();
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
    public void ajouterMedicament(Medicament m) {
        try {
            PreparedStatement st = cnx.prepareStatement("INSERT into medicament"
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
    public void modifierMedicament(Medicament m) {
        try {
            String req = "UPDATE medicament set nom=?,"
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
            String req = "DELETE from medicament where id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Medicament Supprime");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Medicament trouverParId(int id)
    {
        Medicament m = new Medicament();
        try{
            String req = "SELECT * from medicament where id="+id;
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
    public List<Medicament> trierMedicament(String str1, String str2) {
        List<Medicament> listeMedicaments = new ArrayList<>();
        String critere = convertiCritere(str1);
        String ordre = convertirOrdre(str2);
        
        try{
            String req = "Select * from medicament order by "+critere+" "+ordre;
            Statement st =cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                Medicament m = new Medicament();
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
