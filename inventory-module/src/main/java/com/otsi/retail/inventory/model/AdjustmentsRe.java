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
@Table(name = "AdjustmentsRe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentsRe {

	@Id
	@GeneratedValue
	private Long adjustmentReId;
	private String currentBarcodeId;
	private String toBeBarcodeId;
	private String createdBy;
	private String comments;
	private LocalDate creationDate;
	private LocalDate lastModifiedDate;

}
