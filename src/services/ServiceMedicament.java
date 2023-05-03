/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Medicament;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.Connexion;

/**
 *
 * @author MSI
 */
public class ServiceMedicament {
     Connection cnx;
     
       public ServiceMedicament() {
        cnx = Connexion.getInstance().getCnx();
    }
       
       
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
}
