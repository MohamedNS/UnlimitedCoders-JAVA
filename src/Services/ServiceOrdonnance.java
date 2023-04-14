/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entity.Medicament;
import java.sql.*;
import java.util.*;
import Utils.MyConnection;
import Entity.Ordonnance;
import interfaces.InterfaceOrdonnance;


/**
 *
 * @author bytesudoer
 */
public class ServiceOrdonnance implements InterfaceOrdonnance{

    Connection cnx;

    public ServiceOrdonnance() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public List<Ordonnance> afficherOrdonnance() {
        List<Ordonnance> ordonnances = new ArrayList<>();
        ServiceMedicament sv = new ServiceMedicament();

        try {
            String req = "SELECT * from ordonnance";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Ordonnance o = new Ordonnance();
                o.setId(rs.getInt("id"));
                o.setValidite(rs.getInt("validite"));
                o.setConsultation_id(rs.getInt("consultation_id"));
                String reqManytoMany = "SELECT * from ordonnance_medicament where ordonnance_id="+o.getId();
                Statement stManytoMany = cnx.createStatement();
                ResultSet rsMedicament = stManytoMany.executeQuery(reqManytoMany);
                while(rsMedicament.next())
                {
                    Medicament m = sv.trouverParId(rsMedicament.getInt("medicament_id"));
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
    public void ajouterOrdonnance(Ordonnance o) {
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
    public void modifierOrdonnance(Ordonnance o) {
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
    
}
