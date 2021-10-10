package com.otsi.retail.inventory.vo;

import java.util.Date;
import com.otsi.retail.inventory.commons.Categories;
import lombok.Data;

@Data
public class CatalogVo {

	private Long id;
	private String name;
	private Categories description;
	private int status;
	private Long CUID = 0L;
	private Date createDate;
	private Date lastModified;

}
