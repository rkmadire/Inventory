package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AdjustmentsReVo {

	private Long adjustmentReId;
	private String currentBarcodeId;
	private String toBeBarcodeId;
	private String createdBy;
	private String comments;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Long storeId;
}
