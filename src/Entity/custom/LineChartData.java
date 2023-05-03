package Entity.custom;

import java.sql.Date;

public class LineChartData {
	
	private Date date;
	
	private int number;

	public LineChartData(Date date, int number) {
		super();
		this.date = date;
		this.number = number;
	}

	public LineChartData() {
		// TODO Auto-generated constructor stub
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
