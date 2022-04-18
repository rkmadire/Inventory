/**
 * 
 */
package com.otsi.retail.inventory.vo;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.Categories;

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
	 @Enumerated(EnumType.STRING)
	private Categories description;
	private int status;
	private Long CUID = 0L;
	private Date createDate;
	private Date lastModified;

}
