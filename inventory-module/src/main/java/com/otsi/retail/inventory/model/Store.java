package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
	
	@Id
	@GeneratedValue
	private Long storeId;

	private String storeName;

	private String StoreDescription;

	private Long suid;

	private LocalDate creationdate;

	private LocalDate lastmodified;

}
