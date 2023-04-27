package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.custom.BarChartData;
import entities.custom.LineChartData;
import entities.custom.PieChartData;
import utils.MyConnection;

public class ChartsQueries {
	
	Statement ste;
	Connection conn = MyConnection.getInstance().getConn();
	
	//FOR PIE CHART
	public List<PieChartData> pieChartData() throws SQLException {
		String req = "SELECT COUNT(*) as nbr, categorie.nom as cat FROM produit INNER JOIN categorie ON categorie.id = produit.categorie_id GROUP BY categorie.nom;";
		ResultSet rs = conn.prepareStatement(req).executeQuery();
		List<PieChartData> pcds = new ArrayList<PieChartData>();
		while (rs.next()) {
			PieChartData pcd = new PieChartData();
			pcd.setCategory(rs.getString("cat"));
			pcd.setProducts(rs.getInt("nbr"));
			pcds.add(pcd);
		}
		return pcds;
	}
	
	public List<BarChartData> barChartData() throws SQLException {
		String req = "SELECT produit.nom as prd , COUNT(*) as cmd FROM `commande_produit` INNER JOIN produit ON produit.id = commande_produit.id_produit GROUP BY produit.nom ORDER BY COUNT(*) DESC LIMIT 3";
		ResultSet rs = conn.prepareStatement(req).executeQuery();
		List<BarChartData> bcds = new ArrayList<BarChartData>();
		while (rs.next()) {
			BarChartData bcd = new BarChartData();
			bcd.setProduct(rs.getString("prd"));
			bcd.setNumber(rs.getInt("cmd"));
			bcds.add(bcd);
		}
		return bcds;
	}
	
	public List<LineChartData> lineChartData() throws SQLException {
		String req = "SELECT DATE(date_ajout) AS dt, COUNT(*) AS cmds FROM `commande` GROUP BY DATE(date_ajout);";
		ResultSet rs = conn.prepareStatement(req).executeQuery();
		List<LineChartData> lcds = new ArrayList<LineChartData>();
		while (rs.next()) {
			LineChartData lcd = new LineChartData();
			lcd.setDate(rs.getDate("dt"));
			lcd.setNumber(rs.getInt("cmds"));
			lcds.add(lcd);
		}
		return lcds;
	}

}
