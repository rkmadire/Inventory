package com.otsi.retail.inventory.mapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.vo.BarcodeVo;
import com.otsi.retail.inventory.vo.CatalogVo;

@Component
public class BarcodeMapper {
	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public BarcodeVo EntityToVo(Barcode dto) {
		BarcodeVo vo = new BarcodeVo();
		vo.setBarcodeId(dto.getBarcodeId());
		vo.setBarcode(dto.getBarcode());
		CatalogVo catalog = new CatalogVo();
		catalog.setId(dto.getDefaultCategoryId());
		vo.setDefaultCategoryId(Arrays.asList(catalog));
		vo.setAttr1(dto.getAttr1());
		vo.setAttr2(dto.getAttr2());
		vo.setAttr3(dto.getAttr3());
		vo.setCreationDate(LocalDate.now());
		vo.setLastModified(LocalDate.now());
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<BarcodeVo> EntityToVo(List<Barcode> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public Barcode VoToEntity(BarcodeVo vo) {
		Barcode dto = new Barcode();
		dto.setBarcodeId(vo.getBarcodeId());
		dto.setBarcode(vo.getBarcode());
		// dto.setDefaultCategoryId(vo.getDefaultCategoryId());
		dto.setAttr1(vo.getAttr1());
		dto.setAttr2(vo.getAttr2());
		dto.setAttr3(vo.getAttr3());
		dto.setCreationDate(LocalDate.now());
		dto.setLastModified(LocalDate.now());
		return dto;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<Barcode> VoToEntity(List<BarcodeVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
