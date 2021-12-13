package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "barcode_textile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeTextile {

	@Id
	@GeneratedValue
	private Long barcodeTextileId;
	private String barcode;
	private Long division;
	private Long section;
	private Long subSection;
	private Long category;
	private String batchNo;
	private String colour;
	private String attr_7;
	private String attr_8;
	private String attr_9;
	private String attr_10;
	private String attr_11;
	private String attr_12;
	private String attr_13;
	private String attr_14;
	private String attr_15;
	private String attr_16;
	private String attr_17;
	private String attr_18;
	private String attr_19;
	private String attr_20;
	private LocalDate creationDate;
	private LocalDate lastModified;
	@OneToOne(mappedBy = "barcodeTextile")
	@JsonBackReference
	private ProductTextile productTextile;
	
}
