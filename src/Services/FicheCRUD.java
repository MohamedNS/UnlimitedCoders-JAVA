package Services;

import Entity.Fiche;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Utils.MyConnection;

public class FicheCRUD implements IFicheService<Fiche> {

    Connection cnx;
    Statement stm;
    ArrayList<Fiche> list;

    public FicheCRUD() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public List<String> listerIDsFiches() {
        List<String> fiches = new ArrayList<>();
        try {
            String qry = "SELECT id_fiche FROM fiche";
            stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                fiches.add(rs.getString("id_fiche"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return fiches;
    }

    public int getIdPatient(int idFiche) {
        int idPatient = 0;
        try {
            String qry = "SELECT id_patient FROM fiche WHERE id_fiche = ?";
            PreparedStatement pst = cnx.prepareStatement(qry);
            pst.setInt(1, idFiche);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idPatient = rs.getInt("id_patient");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return idPatient;
    }

    public LocalDate getDateFiche(int idFiche) {
        LocalDate dateFiche = null;
        try {
            Connection conn = MyConnection.getInstance().getConnection();
            String query = "SELECT date_fiche FROM fiche WHERE id_fiche=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idFiche);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dateFiche = rs.getDate("date_fiche").toLocalDate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return dateFiche;
    }

}
