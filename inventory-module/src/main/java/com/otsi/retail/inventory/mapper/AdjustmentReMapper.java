package com.otsi.retail.inventory.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.otsi.retail.inventory.model.AdjustmentsRe;
import com.otsi.retail.inventory.vo.AdjustmentsReVo;

@Component
public class AdjustmentReMapper {

	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public AdjustmentsReVo EntityToVo(AdjustmentsRe dto) {
		AdjustmentsReVo vo = new AdjustmentsReVo();
		vo.setAdjustmentReId(dto.getAdjustmentReId());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setCurrentBarcodeId(dto.getCurrentBarcodeId());
		vo.setToBeBarcodeId(dto.getToBeBarcodeId());
		vo.setFromDate(dto.getCreationDate());
		vo.setToDate(dto.getLastModifiedDate());
		vo.setComments(dto.getComments());

		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<AdjustmentsReVo> EntityToVo(List<AdjustmentsRe> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public AdjustmentsRe VoToEntity(AdjustmentsReVo vo) {
		AdjustmentsRe dto = new AdjustmentsRe();
		dto.setAdjustmentReId(vo.getAdjustmentReId());
		dto.setCreatedBy(vo.getCreatedBy());
		dto.setCurrentBarcodeId(vo.getCurrentBarcodeId());
		dto.setToBeBarcodeId(vo.getToBeBarcodeId());
		dto.setCreationDate(LocalDate.now());
		dto.setLastModifiedDate(LocalDate.now());
		dto.setComments(vo.getComments());
		return dto;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<AdjustmentsRe> VoToEntity(List<AdjustmentsReVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
