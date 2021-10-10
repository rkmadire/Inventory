package com.otsi.retail.inventory.commons;

/**
 * @author Sudheer.Swamy
 *
 */

public enum Categories {

	None(0L, "none"), Main_Category(1L, "main_category"), Sub_Category(2L, "sub_category"),
	Leaf_Category(3L, "leaf_category");

	private Long id;
	private String name;

	/**
	 * @param id
	 * @param name
	 */
	private Categories(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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
