/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Entity.custom;

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
