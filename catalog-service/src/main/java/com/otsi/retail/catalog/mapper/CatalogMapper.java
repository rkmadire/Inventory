/**
 * 
 */
package com.otsi.retail.catalog.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.otsi.retail.catalog.model.CatalogEntity;
import com.otsi.retail.catalog.vo.CatalogVo;

/**
 * @author Sudheer.Swamy
 *
 */
@Component
public class CatalogMapper {

	public CatalogEntity convertVoToEntity(CatalogVo vo) {

		CatalogEntity entity = new CatalogEntity();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setDescription(vo.getDescription());
		entity.setStatus(vo.getStatus());
		entity.setCUID(vo.getCUID());
		entity.setCreateDate(new Date());
		entity.setLastModified(new Date());

		return entity;

	}

	public CatalogVo convertEntityToVo(CatalogEntity entity) {

		CatalogVo vo = new CatalogVo();
		entity.setId(vo.getId());
		vo.setName(entity.getName());
		vo.setDescription(entity.getDescription());
		vo.setStatus(entity.getStatus());
		vo.setCUID(entity.getCUID());
		vo.setCreateDate(new Date());
		vo.setLastModified(new Date());

		return vo;

	}

	public List<CatalogVo> convertEntityToVo(List<CatalogEntity> dtos) {
		return dtos.stream().map(entity -> convertEntityToVo(entity)).collect(Collectors.toList());

	}

}
