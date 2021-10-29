package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProductTextileVo {

	private Long productTextileId;
	private BarcodeTextileVo barcodeTextile;
	private String parentBarcode;
	private float costPrice;
	private float itemMrp;
	private float itemRsp;
	private StoresVo store;
	private String promoLabel;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private String uom;
	private int hsnMasterId;
	private String originalBarcode;
	private LocalDate originalBarcodeCreatedAt;
	private int createForLocation;
	private float valueAdditionCp;
	private String itemCode;
	private String itemSku;
	private String attr_21;
	private String attr_22;
	private String attr_23;
	private String attr_24;
	private String attr_25;
	
	
}
