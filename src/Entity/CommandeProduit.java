package entities;

public class CommandeProduit {
	
	int id_CommandeProduit;
	
	int id_commande;
	
	int id_produit;
	
	int qnt;

	public int getId_commande() {
		return id_commande;
	}

	public void setId_commande(int id_commande) {
		this.id_commande = id_commande;
	}

	public int getId_produit() {
		return id_produit;
	}

	public void setId_produit(int id_produit) {
		this.id_produit = id_produit;
	}

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}

	public CommandeProduit(int id_CommandeProduit, int id_commande, int id_produit, int qnt) {
		super();
		this.id_CommandeProduit = id_CommandeProduit;
		this.id_commande = id_commande;
		this.id_produit = id_produit;
		this.qnt = qnt;
	}

	public CommandeProduit() {
		super();
	}
	
	
	
	

}
