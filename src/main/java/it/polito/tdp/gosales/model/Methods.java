package it.polito.tdp.gosales.model;

import java.util.Objects;

public class Methods {
	
	private int code;
	private String type;
	
	public Methods(int code, String type) {
		super();
		this.code = code;
		this.type = type;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
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

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Methods other = (Methods) obj;
		return code == other.code;
	}
	
	
	
}