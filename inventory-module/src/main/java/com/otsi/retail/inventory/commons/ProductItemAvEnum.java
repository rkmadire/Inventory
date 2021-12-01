package com.otsi.retail.inventory.commons;

public enum ProductItemAvEnum {
	
	COLOR(0,"color"),LENGTH(1,"length"),PRODUCT_VALIDITY(2,"productValidity");

	private int id;
	private String eName;

	private ProductItemAvEnum(int id, String eName) {

		this.id = id;
		this.eName = eName;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

}
