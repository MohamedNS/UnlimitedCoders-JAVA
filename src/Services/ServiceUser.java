package Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entities.User;
import ConnectionDB.MyConnection;
import Entities.Role;
import java.sql.ResultSet;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;

public class ServiceUser {
public static UserSession userSession;

    public boolean register(User user) {
        String roleType = "";
        roleType = "[\"ROLE_USER\"]";

        try {
            /*String requete = "insert into user (email,roles,password,nom,prenom)values (?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, user.getEmail());
            pst.setString(2, roleType);
            pst.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            pst.setString(4, user.getNom());
            pst.setString(5, user.getPrenom());*/
            
            String requete = "insert into user (nom,prenom,email,password,roles)values (?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt() ));
            pst.setString(5, roleType) ;


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

    public static User login(String email, String password) {
        System.out.println(email);
        System.out.println(password);

        User user = null;
        String pass = "";
        String role = null;
        try {
            String requete = "Select email,password from user where email = ?";
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
                requete = "Select nom,prenom,email,telephone from user where email = ?";
                pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
                pst.setString(1, email);
                rs = pst.executeQuery();
                while (rs.next()) {
                    user = new User(rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("email"));
                }
                System.out.println(BCrypt.checkpw(password, pass));
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
            String requete = "Select nom,prenom,email,telephone,image from user where email = ?";
            PreparedStatement pst = MyConnection.getInstance().getConnection().prepareStatement(requete);
            pst.setString(1, mail);
            ResultSet rs;
            rs = pst.executeQuery();

            while (rs.next()) {
                user = new User(rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("telephone"), rs.getString("image"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

            return null;
        }
        //System.out.println(user);
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

}
