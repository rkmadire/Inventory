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

	private String empId;

	private int status;

	private String title;

	private String hsnCode;

	private int stock;

	private String discontinued;

	private String parentbarcode;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String name;

	private float costPrice;

	private float listPrice;

	private String uom;

	private String barcodeId;

	private Long storeId;

	private Long domainDataId;

	private String colour;

	private int length;

	private LocalDate productValidity;

	private List<ProductImage> ProductImage;

	private int stockValue;

	private Boolean isBarcode;

	private String batchNo;

	private float value;

}
