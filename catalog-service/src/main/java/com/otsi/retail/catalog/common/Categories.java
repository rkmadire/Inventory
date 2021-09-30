/**
 * 
 */
package com.otsi.retail.catalog.common;

/**
 * @author Sudheer.Swamy
 *
 */
public enum Categories {

	Main_Category(1,"main_category"), Sub_Category(2,"sub_category"), Leaf_Category(3,"leaf_category");
	
	private int id;
	private String name;
	
	/**
	 * @param id
	 * @param name
	 */
	private Categories(int id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
