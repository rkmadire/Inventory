package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import java.util.List;

import com.otsi.retail.inventory.model.ProductImage;

import lombok.Data;

@Data
public class ProductItemVo {

	private Long productItemId;

	private String tyecode;

	private String defaultImage;

	private int status;

	private String title;

	private int stock;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String name;

	private float costPrice;

	private float listPrice;

	private String uom;

	private Long barcodeId;

	private Long storeId;

	private Long domainDataId;

	private String color;

	private int length;

	private LocalDate productValidity;

	private List<ProductImage> ProductImage;

	private int stockValue;

}
