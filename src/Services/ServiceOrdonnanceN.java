/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Medicament;
import Entity.MedicamentN;
import java.sql.*;
import java.util.*;
import Utils.MyConnection;
import Entity.Ordonnance;
import Entity.OrdonnanceN;
import interfaces.InterfaceOrdonnance;


/**
 *
 * @author bytesudoer
 */
public class ServiceOrdonnanceN implements InterfaceOrdonnance{

    Connection cnx;

    public ServiceOrdonnanceN() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public List<OrdonnanceN> afficherOrdonnance() {
        List<OrdonnanceN> ordonnances = new ArrayList<>();
        ServicesMedicamentN sv = new ServicesMedicamentN();

        try {
            String req = "SELECT * from ordonnance";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                OrdonnanceN o = new OrdonnanceN();
                o.setId(rs.getInt("id"));
                o.setValidite(rs.getInt("validite"));
                o.setConsultation_id(rs.getInt("consultation_id"));
                String reqManytoMany = "SELECT * from ordonnance_medicament where ordonnance_id="+o.getId();
                Statement stManytoMany = cnx.createStatement();
                ResultSet rsMedicament = stManytoMany.executeQuery(reqManytoMany);
                while(rsMedicament.next())
                {
                    MedicamentN m = sv.trouverParId(rsMedicament.getInt("medicament_id"));
                    //System.out.println("Medicament Trouve : "+m);
                    o.setNomMedicament();
                    o.addMedicament(m);
                }
                ordonnances.add(o);
            }
            //System.out.println(ordonnances);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }

    @Override
    public void ajouterOrdonnance(OrdonnanceN o) {
        try {
            PreparedStatement st = cnx.prepareStatement("INSERT into ordonnance"
                    + "(consultation_id,validite)"
                    + "values(?,?)");
            st.setInt(1, o.getConsultation_id());
            st.setInt(2, o.getValidite());
            st.executeUpdate();
            /*PreparedStatement stManyToMany = cnx.prepareStatement("INSERT into ordonnance_medicament"
                    + "(ordonnance_id,medicament_id)"
                    + "values(?,?");
            stManyToMany.setInt(1, o.getId());
            stManyToMany.setInt(2, m.getId());
            stManyToMany.executeUpdate();*/
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifierOrdonnance(OrdonnanceN o) {
        try {
            String req = "UPDATE ordonnance set consultation_id=?,"
                    + "validite=? where id=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, o.getConsultation_id());
            st.setInt(2, o.getValidite());
            st.setInt(3, o.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerOrdonnance(int id) {
        try {
            String req = "DELETE from ordonnance where id=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("ordonnance supprimee");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<OrdonnanceN> trierOrdonnances(String str1, String str2) {
        List<OrdonnanceN> listeOrdonnances = new ArrayList<>();
        String critere = converitCritere(str1);
        String ordre = convertirOrdre(str2);
        ServicesMedicamentN sv = new ServicesMedicamentN();
        try {
            String req = "Select * from ordonnance order by "+critere+" "+ordre;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next())
            {
                OrdonnanceN o = new OrdonnanceN();
                o.setId(rs.getInt("id"));
                o.setValidite(rs.getInt("validite"));
                o.setConsultation_id(rs.getInt("consultation_id"));
                String reqManytoMany = "SELECT * from ordonnance_medicament where ordonnance_id ="+o.getId();
                Statement stManytoMany = cnx.createStatement();
                ResultSet rsManytoMany = stManytoMany.executeQuery(reqManytoMany);
                while(rsManytoMany.next())
                {
                    MedicamentN m = sv.trouverParId(rsManytoMany.getInt("medicament_id"));
                    o.setNomMedicament();
                    o.addMedicament(m);
                }
                listeOrdonnances.add(o);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listeOrdonnances;
    }

    @Override
    public String converitCritere(String str) {
        String val = "";
        switch (str) {
            case "Identifiant":
                val = "id";
                break;
            case "Consultation":
                val = "consultation_id";
                break;
            case "Validite":
                val = "validite";
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
