package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import com.otsi.retail.inventory.model.ProductItem;
import lombok.Data;

@Data
public class ProductImageVo {

	private Long productImageId;

	private ProductItem productItem;

	private String image;

	private Long pIUID;

	private LocalDate creationDate;

	private LocalDate lastModified;

}
