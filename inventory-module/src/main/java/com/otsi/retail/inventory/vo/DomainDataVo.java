package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DomainDataVo {

	private Long domainDataId;

	private String domainName;

	private String description;

	private Long duid;

	private LocalDate creationdate;

	private LocalDate lastmodified;
}
