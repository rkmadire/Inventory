package com.otsi.retail.barcode.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "barcode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Barcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

