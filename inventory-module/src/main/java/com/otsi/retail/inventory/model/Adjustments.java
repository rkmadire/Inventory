package com.otsi.retail.inventory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Adjustments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adjustments {

	@Id
	@GeneratedValue
	private Long adjustmentId;
	private String currentBarcodeId;
	private String toBeBarcodeId;
	private String createdBy;
	private String comments;
	private LocalDate creationDate;
	private LocalDate lastModifiedDate;

}
