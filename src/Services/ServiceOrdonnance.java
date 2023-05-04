/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Medicament;
import Entity.Ordonnance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Utils.MyConnection;
/**
 *
 * @author MSI
 */
public class ServiceOrdonnance {
    Connection cnx;

    public ServiceOrdonnance() {
        cnx = MyConnection.getInstance().getConnection();
    }

    
       public List<Ordonnance> afficherOrdonnance() {
        List<Ordonnance> ordonnances = new ArrayList<>();
        ServiceMedicament sv = new ServiceMedicament();

        try {
            String req = "SELECT * from ordonnanceA";
            Statement st =  cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Ordonnance o = new Ordonnance();
                o.setId(rs.getInt("id"));
                o.setValidite(rs.getInt("validite"));
                o.setConsultation_id(rs.getInt("consultation_id"));
                o.setCode(rs.getString("code"));
                String reqManytoMany = "SELECT * from ordonnance_medicament where ordonnance_id="+o.getId();
                Statement stManytoMany =  cnx.createStatement();
                ResultSet rsMedicament = stManytoMany.executeQuery(reqManytoMany);
                while(rsMedicament.next())
                {
                    Medicament m = sv.trouverParId(rsMedicament.getInt("medicament_id"));
                    System.out.println("Medicament Trouve : "+m);
                    //o.setNomMedicament();
                    //o.addMedicament(m);
                }
                ordonnances.add(o);
            }
            //System.out.println(ordonnances);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ordonnances;
    }
}
