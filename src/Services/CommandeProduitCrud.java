/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import entities.CommandeProduit;
import entities.DetailsCommandeProduit;
import Entity.commande;
import Utils.MyConnection;

public class CommandeProduitCrud {

	Statement ste;
	
	PreparedStatement ps;

	Connection conn = MyConnection.getInstance().getConnection();

	public int ajouterCommande() throws SQLException {
	    int id = 0;
	    String req = "INSERT INTO commande (prix) VALUES (0)";

	    try (PreparedStatement ps = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
	        ps.executeUpdate();
	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                id = generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating commande failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	    
	    return id;
	}

	public List<commande> getCommandes() throws SQLException {
		List<commande> commandes = new ArrayList<commande>();
		String req = "SELECT * FROM commande";
		ste = conn.createStatement();
		ResultSet rs = ste.executeQuery(req);
		while (rs.next()) {
			commande cmd = new commande();
			cmd.setDate(rs.getDate("date_ajout"));
			cmd.setPrix(rs.getDouble("prix"));
			cmd.setId(rs.getInt("id"));
			commandes.add(cmd);
		}
		return commandes;
	}

	public double getProduitPrix(int id) throws SQLException {
		double prix = 0;
		String req = "SELECT prix FROM produit WHERE id =" + id;
		ste = conn.createStatement();
		ResultSet rs = ste.executeQuery(req);
		while (rs.next()) {
			prix = prix + rs.getDouble(1);
		}
		return prix;
	}

	public List<DetailsCommandeProduit> detailsCommande(int id_commande) throws SQLException {
		List<DetailsCommandeProduit> lst = new ArrayList<DetailsCommandeProduit>();
		String req = "SELECT " + "produit.nom ," + " produit.prix ," + " commande_produit.quantite ,"
				+ " produit.prix * commande_produit.quantite " + "FROM commande_produit "
				+ "INNER JOIN produit ON produit.id = commande_produit.id_produit "
				+ "WHERE commande_produit.id_commande = "+id_commande;
		ste = conn.createStatement();
		ResultSet rs = ste.executeQuery(req);
		while (rs.next()) {
			DetailsCommandeProduit dcp = new DetailsCommandeProduit();
			dcp.setNomProduit(rs.getString(1));
			dcp.setPrixUnitaire(rs.getDouble(2));
			dcp.setQuantite(rs.getInt(3));
			dcp.setPrixTotal(rs.getDouble(4));
			lst.add(dcp);
		}
		System.out.println(lst.toArray().toString());
		return lst;

	}

	
	//POSSIBLE METIEr
	public void addDetails(CommandeProduit cp) throws SQLException {
		String req = "INSERT INTO commande_produit (id_commande, id_produit, quantite) " + "VALUES ("
				+ cp.getId_commande() + "," + cp.getId_produit() + "," + cp.getQnt() + ")";
		ste = conn.createStatement();
		ste.executeUpdate(req);
		double prixToAggregate = this.getProduitPrix(cp.getId_produit()) * cp.getQnt();
		String req2 = "UPDATE commande SET prix = prix + " + prixToAggregate;
		ste.executeUpdate(req2);
	}
	
	
	
	

}
