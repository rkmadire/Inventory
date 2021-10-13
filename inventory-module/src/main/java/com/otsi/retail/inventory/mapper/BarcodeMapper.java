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
		
		vo.setAttr_1(dto.getAttr_1());
		vo.setAttr_2(dto.getAttr_2());
		vo.setAttr_3(dto.getAttr_3());
		vo.setAttr_4(dto.getAttr_4());
		vo.setAttr_5(dto.getAttr_5());
		vo.setAttr_6(dto.getAttr_6());
		vo.setAttr_7(dto.getAttr_7());
		vo.setAttr_8(dto.getAttr_8());
		vo.setAttr_9(dto.getAttr_9());
		vo.setAttr_10(dto.getAttr_10());
		vo.setAttr_11(dto.getAttr_11());
		vo.setAttr_12(dto.getAttr_12());
		vo.setAttr_13(dto.getAttr_13());
		vo.setAttr_14(dto.getAttr_14());
		vo.setAttr_15(dto.getAttr_15());
		vo.setAttr_16(dto.getAttr_16());
		vo.setAttr_17(dto.getAttr_17());
		vo.setAttr_18(dto.getAttr_18());
		vo.setAttr_19(dto.getAttr_19());
		vo.setAttr_20(dto.getAttr_20());
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
		dto.setAttr_1(vo.getAttr_1());
		dto.setAttr_2(vo.getAttr_2());
		dto.setAttr_3(vo.getAttr_3());
		dto.setAttr_4(vo.getAttr_4());
		dto.setAttr_5(vo.getAttr_5());
		dto.setAttr_6(vo.getAttr_6());
		dto.setAttr_7(vo.getAttr_7());
		dto.setAttr_8(vo.getAttr_8());
		dto.setAttr_9(vo.getAttr_9());
		dto.setAttr_10(vo.getAttr_10());
		dto.setAttr_11(vo.getAttr_11());
		dto.setAttr_12(vo.getAttr_12());
		dto.setAttr_13(vo.getAttr_13());
		dto.setAttr_14(vo.getAttr_14());
		dto.setAttr_15(vo.getAttr_15());
		dto.setAttr_16(vo.getAttr_16());
		dto.setAttr_17(vo.getAttr_17());
		dto.setAttr_18(vo.getAttr_18());
		dto.setAttr_19(vo.getAttr_19());
		dto.setAttr_20(vo.getAttr_20());
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
