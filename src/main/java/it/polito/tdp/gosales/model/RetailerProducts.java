package it.polito.tdp.gosales.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Helper class per la creazione degli archi, aggregando i retailers lato codice.
 * @author carlo
 *
 */
public class RetailerProducts {
	
	private Retailers retailer;
	Set<Integer> soldProducts;
	
	
	public RetailerProducts(Retailers retailer) {
		super();
		this.retailer = retailer;
		this.soldProducts = new HashSet<Integer>();
	}
	
	public RetailerProducts(Retailers retailer, Set<Integer> soldProducts) {
		super();
		this.retailer = retailer;
		this.soldProducts = soldProducts;
	}


	public Retailers getRetailer() {
		return retailer;
	}


	public void setRetailer(Retailers retailer) {
		this.retailer = retailer;
	}


	public Set<Integer> getSoldProducts() {
		return soldProducts;
	}


	public void setSoldProducts(Set<Integer> soldProducts) {
		this.soldProducts = soldProducts;
	}
	
	
	public void addProduct(Integer p) {
		this.soldProducts.add(p);
	}

	@Override
	public int hashCode() {
		return Objects.hash(retailer, soldProducts);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetailerProducts other = (RetailerProducts) obj;
		return Objects.equals(retailer, other.retailer) && Objects.equals(soldProducts, other.soldProducts);
	}
	
		

}
