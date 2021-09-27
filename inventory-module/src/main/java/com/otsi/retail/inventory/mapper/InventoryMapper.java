package com.otsi.retail.inventory.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.otsi.retail.inventory.model.Inventory;
import com.otsi.retail.inventory.vo.InventoryVo;

@Component
public class InventoryMapper {

	public InventoryVo EntityToVo(Inventory dto) {
		InventoryVo vo = new InventoryVo();
		vo.setInventoryId(dto.getInventoryId());
		vo.setBarcode(dto.getBarcode());
		vo.setDescription(dto.getDescription());
		vo.setDiscontinued(dto.getDiscontinued());
		vo.setInventoryValue(dto.getInventoryValue());
		vo.setQtyInStock(dto.getQtyInStock());
		vo.setUnitPrice(dto.getUnitPrice());
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<InventoryVo> EntityToVo(List<Inventory> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public Inventory VoToEntity(InventoryVo vo) {
		Inventory dto = new Inventory();
		dto.setInventoryId(vo.getInventoryId());
		dto.setBarcode(vo.getBarcode());
		dto.setDescription(vo.getDescription());
		dto.setDiscontinued(vo.getDiscontinued());
		dto.setInventoryValue(vo.getInventoryValue());
		dto.setQtyInStock(vo.getQtyInStock());
		dto.setUnitPrice(vo.getUnitPrice());
		return dto;

	}
	/*
	 * to convert list vo's to dto's
	 */

	public List<Inventory> VoToEntity(List<InventoryVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
