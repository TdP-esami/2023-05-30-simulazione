package it.polito.tdp.gosales.model;

import java.util.Objects;

public class Products {
	
	private int number;
	private String line;
	private String type;
	private String product;
	private String brand;
	private String color;
	private double unit_cost;
	private double unit_price;
	
	public Products(int number, String line, String type, String product, String brand, String color, double unit_cost,
			double unit_price) {
		super();
		this.number = number;
		this.line = line;
		this.type = type;
		this.product = product;
		this.brand = brand;
		this.color = color;
		this.unit_cost = unit_cost;
		this.unit_price = unit_price;
	}
	
	
	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
	
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}
	
	
	/**
	 * @param line the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}
	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	
	
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	
	
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	
	
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	
	
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	
	/**
	 * @return the unit_cost
	 */
	public double getUnit_cost() {
		return unit_cost;
	}
	
	
	/**
	 * @param unit_cost the unit_cost to set
	 */
	public void setUnit_cost(double unit_cost) {
		this.unit_cost = unit_cost;
	}
	
	
	/**
	 * @return the unit_price
	 */
	public double getUnit_price() {
		return unit_price;
	}
	
	
	/**
	 * @param unit_price the unit_price to set
	 */
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		return number == other.number;
	}
	
	
	@Override
	public String toString() {
		return "Prodotto " + number + ": " + product + " " + color;
	}
	

}