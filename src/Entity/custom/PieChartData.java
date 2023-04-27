package entities.custom;

public class PieChartData {
	
	private String category;
	
	private int products;

	public PieChartData(String category, int products) {
		super();
		this.category = category;
		this.products = products;
	}

	public PieChartData() {
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getProducts() {
		return products;
	}

	public void setProducts(int products) {
		this.products = products;
	}
	
	

}
