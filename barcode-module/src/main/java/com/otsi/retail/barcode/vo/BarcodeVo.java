package com.otsi.retail.barcode.vo;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BarcodeVo {

	private Long barcodeId;

	private String barcode;

	private String itemDesc;

	private int qty;

	private Long mrp;

	private Long promoDisc;

	private Long netAmount;

	private Long salesMan;

	private LocalDateTime createdDate;

	private LocalDateTime lastModified;

}
