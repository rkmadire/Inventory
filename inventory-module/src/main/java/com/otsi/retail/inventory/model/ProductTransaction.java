package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.otsi.retail.inventory.commons.NatureOfTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransaction {

	@Id
	@GeneratedValue
	private Long productTransactionId;
	private Long storeId;
	private Long barcodeId;
	private int quantity;
	private String natureOfTransaction;
	private String effectingTable;
	private Long effectingTableId;
	private boolean masterFlag;
	private String comment;
	private LocalDate creationDate;
	private LocalDate lastModified;

}
