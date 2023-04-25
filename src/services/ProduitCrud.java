package services;

import entities.categorie;
import entities.produit;
import interfaces.InterfaceProduit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyConnection;

public class ProduitCrud implements InterfaceProduit {

	Statement ste;
	Connection conn = MyConnection.getInstance().getConn();

	@Override
	public void ajouterProduit(produit p) {
		try {
			System.out.println(p);
			String req = "INSERT INTO produit (prix, nom, matricule_asseu,categorie_id) values (" + p.getPrix() + ",'"
					+ p.getNom() + "','" + p.getMatricule_asseu() + "'," + p.getCategorie() + ")";
			ste = conn.createStatement();
			System.out.println(req);
			ste.executeUpdate(req);
			System.out.println("produit ajoutée avec succés");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void ajouterProduit2(produit p) {
		try {
			String req = "INSERT INTO `produit`(prix, nom, matricule_asseu, categorie_id) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, p.getPrix());
			ps.setString(2, p.getNom());
			ps.setString(3, p.getMatricule_asseu());
			ps.setInt(4, p.getCategorie()); // utilisation de la méthode getId() pour récupérer l'identifiant de la
												// catégorie associée au produit
			ps.executeUpdate();
			System.out.println("Produit ajouté !!");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void modifierProduit(produit prod) {

		try {
			String requete = "UPDATE produit SET Categorie_id=?, nom=?, matricule_asseu=?,prix=? WHERE id=?";
			PreparedStatement pst = conn.prepareStatement(requete);

			pst.setInt(1, prod.getCategorie());
			pst.setString(2, prod.getNom());
			pst.setString(3, prod.getMatricule_asseu());
			pst.setDouble(4, Double.parseDouble(prod.getPrix()));
			pst.setInt(5, prod.getId());
			int resultat = pst.executeUpdate();
			if (resultat == 1) {
				System.out.println("Produit modifié avec succès");
			} else {
				System.out.println("Impossible de modifier le Produit");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	@Override
	public void supprimerProduit(produit p) {
		try {
			String req = "DELETE FROM produit WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, p.getId());
			if (ps.executeUpdate() == 1) {
				System.out.println("produit does not exist");
			} else {
				System.out.println("produit deleted");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	@Override
	public List<produit> afficherProduit() {
		List<produit> list = new ArrayList<>();
		try {
			String req = "SELECT * FROM `produit`";
			PreparedStatement ps = conn.prepareStatement(req);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				produit p = new produit();
				p.setId(rs.getInt("id"));
				p.setNom(rs.getString("nom"));
				p.setMatricule_asseu(rs.getString("matricule_asseu"));
				p.setPrix(rs.getString("prix"));
				p.setCategorie(rs.getInt("categorie_id"));
				list.add(p);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return list;
	}

}
