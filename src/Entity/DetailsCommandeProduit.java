package entities;

public class DetailsCommandeProduit {
	
	String nomProduit;
	
	double prixUnitaire;
	
	int quantite;
	
	double prixTotal;

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

	public double getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	public DetailsCommandeProduit(String nomProduit, double prixUnitaire, int quantite, double prixTotal) {
		super();
		this.nomProduit = nomProduit;
		this.prixUnitaire = prixUnitaire;
		this.quantite = quantite;
		this.prixTotal = prixTotal;
	}

	public DetailsCommandeProduit() {
		super();
	}
	
	

}
