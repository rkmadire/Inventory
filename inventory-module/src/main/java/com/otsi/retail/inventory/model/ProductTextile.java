package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.otsi.retail.inventory.commons.ProductStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_textile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTextile {
	@Id
	@GeneratedValue
	private Long productTextileId;
	private String name;
	private String barcode;
	private Long division;
	private Long section;
	private Long subSection;
	private Long category;
	private String batchNo;
	private String colour;
	private String parentBarcode;
	private float costPrice;
	private float itemMrp;
	private String empId;
	private Long storeId;
	private Long domainId;
	private LocalDate creationDate;
	private LocalDate lastModifiedDate;
	@ApiModelProperty(notes = "unit of measures of the product")
	private String uom;
	private String hsnCode;
	private LocalDate originalBarcodeCreatedAt;
	private ProductStatus status;

}
