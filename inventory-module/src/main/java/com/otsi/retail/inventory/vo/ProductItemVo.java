package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import java.util.List;

import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.model.ProductImage;
import com.otsi.retail.inventory.model.ProductInventory;

import lombok.Data;

@Data
public class ProductItemVo {

	private Long productItemId;

	private String tyecode;

	private String defaultImage;

	private int status;

	private String title;

	private int stock;

	private String name;

	private float costPrice;

	private float listPrice;

	private String uom;

	private List<BarcodeVo> barcode;

	private StoresVo store;

	private DomainDataVo domainData;

	private Long puid;

	private String color;

	private int length;

	private LocalDate productValidity;

	private List<ProductImage> ProductImage;

	private ProductInventory productInventory;

}
