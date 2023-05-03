package Entity.custom;

public class BarChartData {
	
	private String product;
	
	private int number;

	public BarChartData(String product, int number) {
		super();
		this.product = product;
		this.number = number;
	}

	public BarChartData() {
		// TODO Auto-generated constructor stub
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
