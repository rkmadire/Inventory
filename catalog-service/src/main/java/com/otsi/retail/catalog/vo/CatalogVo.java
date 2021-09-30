/**
 * 
 */
package com.otsi.retail.catalog.vo;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.otsi.retail.catalog.common.Categories;

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
	private Categories description;
	private int status;
	private Long CUID;
	private Date createDate;
	private Date lastModified;

}
