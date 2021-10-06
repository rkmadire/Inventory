/**
 * 
 */
package com.otsi.retail.catalog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import com.otsi.retail.catalog.common.Categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sudheer.Swamy
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "catalog_categories")
public class CatalogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	private Categories description;
	private int status;
	@JsonIgnore
	@ManyToOne(targetEntity = CatalogEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private CatalogEntity parent;
	// private Long CUID;
	private Date createDate;
	private Date lastModified;

}
