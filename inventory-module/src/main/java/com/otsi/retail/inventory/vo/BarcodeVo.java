package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BarcodeVo {

	private Long barcodeId;
	private String barcode;
	private List<CatalogVo>defaultCategoryId;
	private String attr1;
	private String attr2;
	private String attr3;
	private LocalDate creationDate;
	private LocalDate lastModified;
}
