package com.otsi.retail.inventory.commons;

public enum NatureOfTransaction {

	PURCHASE(0, "purchase"), PURCHASERETURNS(1, "purchase returns"), TFSOUT(2, "tfsout"), TFSIN(3, "tfsin"),
	REBARPARENT(4, "rebar-parent"), REBARCHILD(5, "rebar-child"), SALE(6, "sale"), SALERETURN(7, "sale returns"),
	ADJUSTMENTS(8, "adjustments"), INHOUSEMANUFACTURING(9, "in-house manufacturing");

	private int id;
	private String name;

	private NatureOfTransaction(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
