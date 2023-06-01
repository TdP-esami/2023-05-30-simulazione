package it.polito.tdp.gosales.model;

import java.time.LocalDate;

public class DailySale {
	
	private int retailer_code;
	private int product_number;
	private int order_method_code;
	private LocalDate date;
	private int quantity;
	private double unit_price;
	private double unit_sale_price;
	
	
	public DailySale(int retailer_code, int product_number, int order_method_code, LocalDate date, int quantity,
			double unit_price, double unit_sale_price) {
		super();
		this.retailer_code = retailer_code;
		this.product_number = product_number;
		this.order_method_code = order_method_code;
		this.date = date;
		this.quantity = quantity;
		this.unit_price = unit_price;
		this.unit_sale_price = unit_sale_price;
	}
	
	/**
	 * @return the retailer_code
	 */
	public int getRetailer_code() {
		return retailer_code;
	}
	
	/**
	 * @param retailer_code the retailer_code to set
	 */
	public void setRetailer_code(int retailer_code) {
		this.retailer_code = retailer_code;
	}
	
	/**
	 * @return the product_number
	 */
	public int getProduct_number() {
		return product_number;
	}
	
	/**
	 * @param product_number the product_number to set
	 */
	public void setProduct_number(int product_number) {
		this.product_number = product_number;
	}
	
	/**
	 * @return the order_method_code
	 */
	public int getOrder_method_code() {
		return order_method_code;
	}
	
	/**
	 * @param order_method_code the order_method_code to set
	 */
	public void setOrder_method_code(int order_method_code) {
		this.order_method_code = order_method_code;
	}
	
	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 * @return the unit price (il prezzo di listino)
	 */ 
	public double getUnit_price() {
		return unit_price;
	}

	/**
	 * 
	 * @param unit_price to be set
	 */
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	/**
	 * 
	 * @return the sale price (prezzo effettivo di vendita, ad esempio dopo sconti)
	 */
	public double getUnit_sale_price() {
		return unit_sale_price;
	}

	/**
	 * 
	 * @param unit_sale_price to be set
	 */
	public void setUnit_sale_price(double unit_sale_price) {
		this.unit_sale_price = unit_sale_price;
	}

	
	
	
	
}