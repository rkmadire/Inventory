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
@Table(name = "product_transaction_re")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTransactionRe {

	@Id
	@GeneratedValue
	private Long productTransactionReId;
	private Long storeId;
	private String barcodeId;
	private int quantity;
	private String natureOfTransaction;
	private String effectingTable;
	private Long effectingTableId;
	private boolean masterFlag;
	private String comment;
	private LocalDate creationDate;
	private LocalDate lastModified;

}
