package com.otsi.retail.inventory.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.model.Store;
import com.otsi.retail.inventory.vo.StoresVo;
@Component
public class StoreMapper {

	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public StoresVo EntityToVo(Store dto) {
		StoresVo vo = new StoresVo();
		vo.setStoreId(dto.getStoreId());
		vo.setStoreName(dto.getStoreName());
		vo.setStoreDescription(dto.getStoreDescription());
		vo.setSuid(dto.getSuid());
		vo.setCreationdate(LocalDate.now());
		vo.setLastmodified(LocalDate.now());
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<StoresVo> EntityToVo(List<Store> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public Store VoToEntity(StoresVo vo) {
		Store dto = new Store();
		dto.setStoreId(vo.getStoreId());
		dto.setStoreName(vo.getStoreName());
		dto.setStoreDescription(vo.getStoreDescription());
		dto.setSuid(vo.getSuid());
		dto.setCreationdate(LocalDate.now());
		dto.setLastmodified(LocalDate.now());
		return dto;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<Store> VoToEntity(List<StoresVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
