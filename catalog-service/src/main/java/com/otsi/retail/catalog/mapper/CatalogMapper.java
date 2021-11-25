/**
 * 
 */
package com.otsi.retail.catalog.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
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
		// entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setDescription(vo.getDescription());
		entity.setStatus(vo.getStatus());
		// entity.setCUID(vo.getCUID());
		// entity.setParent(vo.getCUID());
		entity.setCreateDate(new Date());
		entity.setLastModified(new Date());
		entity.setCatergory(vo.getCategory());

		return entity;

	}

	public List<CatalogVo> convertlistEntityToVo(List<CatalogEntity> pentity) {

		List<CatalogVo> lvo = new ArrayList<CatalogVo>();
		pentity.stream().forEach(x -> {

			CatalogVo vo = new CatalogVo();
			vo.setId(x.getId());
			vo.setName(x.getName());
			vo.setCategory(x.getCatergory());
			vo.setDescription(x.getDescription());
			vo.setStatus(x.getStatus());
			vo.setCUID(x.getParent().getId());
			vo.setCreateDate(new Date());
			vo.setLastModified(new Date());
			lvo.add(vo);
		});

		return lvo;

	}

	public CatalogVo convertEntityToVo(CatalogEntity dtos) {
		CatalogVo vo = new CatalogVo();

		vo.setId(dtos.getId());
		vo.setName(dtos.getName());
		vo.setDescription(dtos.getDescription());
		vo.setStatus(dtos.getStatus());
		// vo.setCUID(dtos.getParent().getId());
		vo.setCreateDate(new Date());
		vo.setLastModified(new Date());

		// BeanUtils.copyProperties(dtos, vo);
		return vo;

	}
	public List<CatalogVo> convertlEntityToVo(List<CatalogEntity> dtos) {
		return dtos.stream().map(entity -> convertEntityToVo(entity)).collect(Collectors.toList());

	}
	

}
