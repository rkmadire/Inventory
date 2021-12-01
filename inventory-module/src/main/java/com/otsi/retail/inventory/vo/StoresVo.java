package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StoresVo {

	private Long storeId;

	private String storeName;

	private String StoreDescription;

	private Long suid;

	private LocalDate creationdate;

	private LocalDate lastmodified;

}
