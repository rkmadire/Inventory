package com.otsi.retail.barcode.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.otsi.retail.barcode.model.Barcode;
import com.otsi.retail.barcode.vo.BarcodeVo;

@Component
public class BarcodeMapper {

	public BarcodeVo EntityToVo(Barcode dto) {
		BarcodeVo vo = new BarcodeVo();
		vo.setBarcodeId(dto.getBarcodeId());
		vo.setBarcode(dto.getBarcode());
		vo.setItemDesc(dto.getItemDesc());
		vo.setMrp(dto.getMrp());
		vo.setNetAmount(dto.getNetAmount());
		vo.setPromoDisc(dto.getPromoDisc());
		vo.setQty(dto.getQty());
		vo.setSalesMan(dto.getSalesMan());
		vo.setCreatedDate(LocalDateTime.now());
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
		dto.setItemDesc(vo.getItemDesc());
		dto.setMrp(vo.getMrp());
		dto.setNetAmount(vo.getNetAmount());
		dto.setPromoDisc(vo.getPromoDisc());
		dto.setQty(vo.getQty());
		dto.setSalesMan(vo.getSalesMan());
		dto.setCreatedDate(LocalDateTime.now());
		return dto;

	}
	/*
	 * to convert list vo's to dto's
	 */

	public List<Barcode> VoToEntity(List<BarcodeVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}
}
