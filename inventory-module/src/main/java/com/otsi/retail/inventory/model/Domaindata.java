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
@Table(name = "domaindata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domaindata {

	@Id
	@GeneratedValue
	private Long domainDataId;
	
	private String domainName;

	private String description;

	private Long duid;

	private LocalDate creationdate;

	private LocalDate lastmodified;

}
