package Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entities.User;
import ConnectionDB.MyConnection;
import Entities.Role;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;




public class ServiceUser {

    public static UserSession userSession;
    Connection cnx;

    public ServiceUser() {
        cnx = MyConnection.getInstance().getConnection();
    }

   public boolean register(User user) throws MessagingException {
        String roleType1 = "";
        roleType1 = "[\"ROLE_USER\"]";
        String ban ="0";
        //Date date = new Date(0);
//        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        LocalDate currentDate = LocalDate.now();

        try {
            String requete = "insert into user (nom,prenom,email,password,roles,telephone,created_at,isbanned)values (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(6, user.getEmail());
            pst.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            pst.setString(5, roleType1);
            pst.setString(3, user.getTelephone());
            pst.setString(7, String.valueOf(currentDate));
            pst.setString(8, String.valueOf(ban));
            String send = user.getTelephone();
            if (pst.executeUpdate() > 0) {
                System.out.println("You have registered successfully.");
                System.out.println(send);
                EnvoiyerEmail.envoyer(send);
                return true;
            } else {
                System.out.println("Something went wrong.");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static User login(String email, String password) {
        User user = null;
        String pass = "";
        String role = null;
        try {
            String requete = "Select email,password,roles from user where email = ?";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, email);
            ResultSet rs;
            rs = pst.executeQuery();
            if (rs.next()) {
                pass = rs.getString("password");
                email = rs.getString("email");
            }
            System.out.println(email);
            if (BCrypt.checkpw(password, pass)) {
                requete = "Select nom,prenom,email,telephone,roles,isbanned from user where email = ?";
                pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
                pst.setString(1, email);
                rs = pst.executeQuery();
                while (rs.next()) {
                    user = new User(rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("email"), rs.getString("roles"), rs.getInt("isbanned"));
                }
                //System.out.println(BCrypt.checkpw(password, pass));
            } else {
                System.out.println("password or email invalid");
                return null;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return user;
    }

    public User GetUserByMailSession(String mail) {
        User user = null;
        try {
            String requete = "Select nom,prenom,email,telephone,image,roles,isbanned from user where email = ?";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, mail);
            ResultSet rs;
            rs = pst.executeQuery();

            while (rs.next()) {
                user = new User(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("image"), rs.getString("roles"), rs.getInt("isbanned"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

            return null;
        }
        System.out.println(user);
        return user;
    }

    public void Update(User u, String mail) {
        try {
            String req = "UPDATE user SET nom = '" + u.getNom() + "', prenom = '"
                    + u.getPrenom() + "', telephone = '"
                    + u.getTelephone() + "' WHERE `email` =  '" + mail + "'";

            Statement st = MyConnection.getInstance().getConnection().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Utilisateur updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addImage(User u, String mail) {
        try {
            String req = "UPDATE user SET image = '" + u.getImage() + "' WHERE `email` =  '" + mail + "'";

            Statement st = MyConnection.getInstance().getConnection().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Image updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<User> afficherlesAgents() {
        ObservableList<User> mylist = FXCollections.observableArrayList();
        try {

            String rq = "SELECT * FROM user WHERE `roles` = '[\"ROLE_AGENT\"]' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(5));
                u.setPrenom(rs.getString(6));
                u.setEmail(rs.getString(2));
                u.setTelephone(rs.getString(7));
                mylist.add(u);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;

    }

    public boolean AddAgent(User user) {
        String roleType2 = "";
        roleType2 = "[\"ROLE_PHARMARCIEN\"]";
        LocalDate currentDate = LocalDate.now();

        try {
            String requete = "insert into user (nom,prenom,email,password,roles,telephone,created_at)values (?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(6, user.getEmail());
            pst.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            pst.setString(5, roleType2);
            pst.setString(3, user.getTelephone());
            pst.setString(7, String.valueOf(currentDate));

            if (pst.executeUpdate() > 0) {
                System.out.println("You have registered successfully.");
                return true;
            } else {
                System.out.println("Something went wrong.");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void modifieruser(User u) {

        try {
            String requete = "UPDATE user SET nom=?, prenom=?, email=?, telephone=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelephone());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
            System.out.println("Le user a été modifié avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerUser(int id) {

        try {
            String requete = "DELETE FROM user WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Le user a été supprimé avec succès !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public ObservableList<User> afficherlesUsers() {
        ObservableList<User> mylist = FXCollections.observableArrayList();
        try {

            String rq = "SELECT * FROM user WHERE `roles` = '[\"ROLE_USER\"]' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(5));
                u.setPrenom(rs.getString(6));
                u.setEmail(rs.getString(2));
                u.setTelephone(rs.getString(7));
                u.setIsbanned(rs.getInt(9));
                mylist.add(u);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;

    }

    public void Ban(User u) {

        try {
            String requete = "UPDATE user SET isbanned='1' WHERE id=?";
            PreparedStatement ps = MyConnection.getInstance().getConnection().prepareStatement(requete);

            ps.setInt(1, u.getId());
            ps.executeUpdate();
            System.out.println("L'etat a été changé avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void DeBan(User u) {

        try {
            String requete = "UPDATE user SET isbanned='0' WHERE id=?";
            PreparedStatement ps = MyConnection.getInstance().getConnection().prepareStatement(requete);

            ps.setInt(1, u.getId());
            ps.executeUpdate();
            System.out.println("L'etat a été changé avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public String sendSMS(String telephone) throws SQLException {

        String requete = "SELECT telephone FROM user WHERE telephone=? ";
        PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
        pst.setString(1, telephone);
        ResultSet rs;
        rs = pst.executeQuery();
        while (rs.next()) {
            telephone = rs.getString("telephone");
            //password = rs.getString("password");
        }
        return telephone;

    }

    public void setToken(String y, String numero) {
        try {
            String req = "UPDATE user SET reset_token = '" + y + "' WHERE telephone =  '" + numero + "'";

            Statement st = MyConnection.getInstance().getConnection().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("code sent !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean verifCode(String token) throws SQLException {

        String requete = "SELECT reset_token FROM user WHERE reset_token=? ";
        PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
        pst.setString(1, token);
        ResultSet rs;
        rs = pst.executeQuery();
        String verif = null;

        while (rs.next()) {
            verif = rs.getString("reset_token");
            //password = rs.getString("password");
        }
        if (verif == null) {
            return false;
        } else {
            return true;
        }

    }

    public void resetPassword(String pass, String telephone) {
        try {
            String req = "UPDATE user SET password = '" + BCrypt.hashpw(pass, BCrypt.gensalt()) + "' WHERE telephone =  '" + telephone + "";

            Statement st = MyConnection.getInstance().getConnection().prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("password updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}