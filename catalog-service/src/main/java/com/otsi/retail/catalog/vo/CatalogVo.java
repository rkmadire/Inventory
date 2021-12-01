/**
 * 
 */
package com.otsi.retail.catalog.vo;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.otsi.retail.catalog.common.Categories;
import com.otsi.retail.catalog.model.CatalogEntity;

import lombok.Data;

/**
 * @author Sudheer.Swamy
 *
 */
@Data
@Component
public class CatalogVo {

	private Long id;
	private String name;
	private String category;
	private Categories description;
	private int status;
	private Long CUID = 0L;
	private Date createDate;
	private Date lastModified;

}
