package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProductItemAvVo {

	private Long productItemAvId;

	private int type;

	private String name;

	private int intValue;

	private String stringValue;

	private LocalDate dateValue;

	private LocalDate lastModified;

}
