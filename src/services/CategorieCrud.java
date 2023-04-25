/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.categorie;

import interfaces.InterfaceCategorie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

/**
 *
 * @author rouja
 */
public class CategorieCrud implements InterfaceCategorie {
	Statement ste;
	Connection conn = MyConnection.getInstance().getConn();

	@Override
	public void ajouterCategorie(categorie p) {
		try {
			String req = "INSERT INTO `categorie`(nom, description) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setString(1, p.getNom());
			ps.setString(2, p.getDescription());
			ps.executeUpdate();
			System.out.println("cat ajouté !!");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void modifierCategorie(int id, String nom, String description) {
		try {
			String requete = "UPDATE categorie SET nom=?, description=? WHERE id=?";
			java.sql.PreparedStatement ps = conn.prepareStatement(requete);
			ps.setString(1, nom);
			ps.setString(2, description);
			ps.setInt(3, id);

			int resultat = ps.executeUpdate();
			if (resultat == 1) {
				System.out.println("Catégorie modifiée avec succès");
			} else {
				System.out.println("Impossible de modifier la catégorie");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void supprimerCategorie(int id) {

		try {
			String req = "DELETE FROM categorie WHERE id=?";
			java.sql.PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, id);
			int resultat = ps.executeUpdate();
			if (resultat == 1) {
				System.out.println("categorie supprimé avec succès");
			} else {
				System.out.println("Impossible de supprimer le categorie");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public List<categorie> afficherCategorie() {
		List<categorie> list = new ArrayList<>();
		try {

			String req = "SELECT * FROM `categorie`";
			Statement st = conn.createStatement();
			ResultSet Rs = st.executeQuery(req);
			while (Rs.next()) {
				categorie p = new categorie();
				p.setId(Rs.getInt(1));
				p.setNom(Rs.getString("Nom"));

				p.setDescription(Rs.getString(3));

				list.add(p);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());

		}
		return list;
	}
	
	public categorie getCategorieById(int id) throws SQLException {
		categorie cat = new categorie();
		String req = "SELECT * FROM categorie WHERE id = "+id;
		ResultSet rs = conn.createStatement().executeQuery(req);
		while (rs.next()) {
			cat.setId(rs.getInt(1));
			cat.setNom(rs.getString(2));
			cat.setDescription(rs.getString(3));
			break;
		}
		return cat;
	}
	
	public categorie getCategorieByNom(String nom) throws SQLException {
		categorie cat = new categorie();
		String req = "SELECT * FROM categorie WHERE nom = '"+nom+"'";
		ResultSet rs = conn.createStatement().executeQuery(req);
		while (rs.next()) {
			cat.setId(rs.getInt(1));
			cat.setNom(rs.getString(2));
			cat.setDescription(rs.getString(3));
			break;
		}
		return cat;
	}

}
