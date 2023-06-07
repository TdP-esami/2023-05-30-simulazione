package it.polito.tdp.gosales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.gosales.model.Arco;
import it.polito.tdp.gosales.model.DailySale;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;

public class GOsalesDAO {
	
	
	/**
	 * Metodo per leggere la lista di tutti i rivenditori dal database
	 * @return
	 */

	public List<Retailers> getAllRetailers(){
		String query = "SELECT * from go_retailers";
		List<Retailers> result = new ArrayList<Retailers>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	/**
	 * Metodo per leggere la lista di tutti i prodotti dal database
	 * @return
	 */
	public List<Products> getAllProducts(){
		String query = "SELECT * from go_products";
		List<Products> result = new ArrayList<Products>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	
	/**
	 * Metodo per leggere la lista di tutte le vendite nel database
	 * @return
	 */
	public List<DailySale> getAllSales(){
		String query = "SELECT * from go_daily_sales";
		List<DailySale> result = new ArrayList<DailySale>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new DailySale(rs.getInt("retailer_code"),
				rs.getInt("product_number"),
				rs.getInt("order_method_code"),
				rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
				rs.getInt("quantity"),
				rs.getDouble("unit_price"),
				rs.getDouble("unit_sale_price")  ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	/**
	 * Metodo che legge le nazioni in cui si trovano i rivenditori
	 * @return
	 */
	public List<String> getNazioni(){
		String query = "Select Distinct r.Country "
				+ "FROM go_retailers r";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add( rs.getString("Country") );
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	/**
	 * Metodo che legge i vertici del grafo dal database, restituendo una lista di Retailers
	 * @param nazione
	 * @return
	 */
	public List<Retailers> getVertici(String nazione){
		String query = "SELECT r.* "
				+ "FROM go_retailers r "
				+ "WHERE r.Country = ?";
		List<Retailers> result = new ArrayList<Retailers>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, nazione);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	
	/**
	 * Metodo che legge gli archi del grafo dal database, restituendo una lista di 
	 * oggetti di tipo Arco
	 * @param nazione
	 * @param anno
	 * @param Nmin
	 * @return
	 */
	public List<Arco> getArchi(String nazione, int anno, int Nmin){
		String query = "SELECT r1.Retailer_code as rCode1, r2.Retailer_code as rCode2, COUNT(DISTINCT s1.Product_number) as N "
				+ "FROM go_retailers r1, go_retailers r2, go_daily_sales s1, go_daily_sales s2 "
				+ "WHERE r1.Country = ? AND r1.Country = r2.Country AND "
				+ "r1.Retailer_code = s1.Retailer_code AND r2.Retailer_code = s2.Retailer_code "
				+ "AND s1.Product_number = s2.Product_number AND "
				+ "r1.Retailer_code < r2.Retailer_code "
				+ "AND YEAR(s1.Date) = ? AND YEAR(s1.Date) = YEAR(s2.Date) "
				+ "GROUP BY r1.Retailer_code, r2.Retailer_code "
				+ "HAVING N >=?";
		/*
		 * Another example of a query that does the same thing
		 */
//		String query2 = "SELECT s1.Retailer_code, s2.Retailer_code, COUNT(DISTINCT s1.Product_number) as N\r\n"
//				+ "FROM go_daily_sales s1, go_daily_sales s2\r\n"
//				+ "WHERE s1.Product_number = s2.Product_number AND\r\n"
//				+ "YEAR(s1.Date) = 2017 AND YEAR(s2.Date)=YEAR(s1.Date)\r\n"
//				+ "AND s1.Retailer_code IN (SELECT r.Retailer_code\r\n"
//				+ "																FROM go_retailers r\r\n"
//				+ "																WHERE r.Country = \"France\")\r\n"
//				+ "AND s2.Retailer_code IN (SELECT r.Retailer_code\r\n"
//				+ "																FROM go_retailers r\r\n"
//				+ "																WHERE r.Country = \"France\")\r\n"
//				+ "AND s1.Retailer_code < s2.Retailer_code\r\n"
//				+ "GROUP BY s1.Retailer_code, s2.Retailer_code\r\n"
//				+ "HAVING N>=3";
		List<Arco> result = new ArrayList<Arco>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, nazione);
			st.setInt(2, anno);
			st.setInt(3, Nmin);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Arco(rs.getInt("rCode1"),
						rs.getInt("rCode2"),
						rs.getInt("N")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	
	/**
	 * Metodo che legge tutti i prodotti di un retailer
	 * @param rCode
	 * @param anno
	 * @return
	 */
	public Set<Integer> getRetailerProducts(Integer rCode, int anno){
		String query = "SELECT DISTINCT s.Product_number "
				+ "FROM go_daily_sales s "
				+ "WHERE YEAR(s.date)=? AND s.Retailer_code = ?";
		Set<Integer> result = new HashSet<Integer>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(2, rCode);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("Product_number"));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	
	/**
	 * Metodo che legge dal database tutti i prodotti che un Retailer ha
	 * venduto in un anno
	 * @param r
	 * @param anno
	 * @return
	 */
	public List<Products> getProductsRetailerYear(Retailers r, int anno){
		String query = "SELECT DISTINCT p.* "
				+ "FROM go_daily_sales s, go_products p "
				+ "WHERE s.Product_number = p.Product_number AND "
				+ "YEAR(s.Date)= ? AND s.Retailer_code = ?";
		List<Products> result = new ArrayList<Products>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, anno);
			st.setInt(2, r.getCode());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	
	/**
	 * Metodo che legge dal database il numero di giorni medion
	 * intercorso tra due vendite che il Retailer r ha fatto del 
	 * prodotto p in un anno
	 * @param r
	 * @param p
	 * @param anno
	 * @return
	 */
	public int getAvgD(Retailers r, Products p, int anno){
		String query = "SELECT s.Retailer_code, 12*30/COUNT(*) AS avgD "
				+ "	FROM go_daily_sales s "
				+ "	WHERE s.Retailer_code = ? AND s.Product_number = ? "
				+ "	AND YEAR(s.Date) = ?";
		int result = 0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, r.getCode());
			st.setInt(2, p.getNumber());
			st.setInt(3, anno);
			ResultSet rs = st.executeQuery();

			if (rs.first()) {
				result = (int)rs.getDouble("avgD");
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	
	/**
	 * Metodo che legge dal database la quantità media
	 * venduta dal Retailer r in un anno, per quanto riguarda il prodotto p
	 * @param r
	 * @param p
	 * @param anno
	 * @return
	 */
	public int getAvgQ(Retailers r, Products p, int anno){
		String query = "SELECT s.Retailer_code, SUM(s.Quantity)/COUNT(*) AS avgQ "
				+ "	FROM go_daily_sales s "
				+ "	WHERE s.Retailer_code = ? AND s.Product_number = ? "
				+ "	AND YEAR(s.Date) = ?";
		int result = 0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, r.getCode());
			st.setInt(2, p.getNumber());
			st.setInt(3, anno);
			ResultSet rs = st.executeQuery();

			if (rs.first()) {
				result = (int)rs.getDouble("avgQ");
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
}
