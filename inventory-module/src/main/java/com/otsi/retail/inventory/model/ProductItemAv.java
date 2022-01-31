package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemAv {

	@Id
	@GeneratedValue//(strategy = GenerationType.IDENTITY)
	private Long productItemAvId;

	private int type;

	private String name;

	private int intValue;

	private String stringValue;

	private LocalDate dateValue;

	private LocalDate lastModified;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "productItemId")
	private ProductItem productItem;

}
