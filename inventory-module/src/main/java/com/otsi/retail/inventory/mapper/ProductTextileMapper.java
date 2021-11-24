package com.otsi.retail.inventory.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.vo.ProductTextileVo;

@Component
public class ProductTextileMapper {

	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public ProductTextileVo EntityToVo(ProductTextile dto) {
		ProductTextileVo vo = new ProductTextileVo();
		BeanUtils.copyProperties(dto, vo);
		
		vo.setCreateForLocation(0);
		vo.setValueAdditionCp(0);
		vo.setQty(dto.getQty());
		vo.setCreatedAt(dto.getCreatedAt());
		vo.setUpdatedAt(dto.getUpdatedAt());
		vo.setOriginalBarcodeCreatedAt(dto.getOriginalBarcodeCreatedAt());
		vo.setStoreId(dto.getStoreId());
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<ProductTextileVo> EntityToVo(List<ProductTextile> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public ProductTextile VoToEntity(ProductTextileVo vo) {
		ProductTextile dto = new ProductTextile();
		BeanUtils.copyProperties(vo, dto);
		dto.setUom("units");
		dto.setCreateForLocation(0);
		dto.setValueAdditionCp(0);
		dto.setQty(1);
		dto.setCreatedAt(LocalDate.now());
		dto.setUpdatedAt(LocalDate.now());
		dto.setOriginalBarcodeCreatedAt(LocalDate.now());
		/*
		 * BarcodeTextile bt = new BarcodeTextile();
		 * bt.setBarcodeTextileId(vo.getBarcodeTextileId()); dto.setBarcodeTextile(bt);
		 */
		dto.setStoreId(vo.getStoreId());
		return dto;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<ProductTextile> VoToEntity(List<ProductTextileVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
