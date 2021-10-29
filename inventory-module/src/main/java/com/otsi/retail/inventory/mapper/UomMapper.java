package com.otsi.retail.inventory.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.otsi.retail.inventory.model.UomEntity;
import com.otsi.retail.inventory.vo.UomVo;

@Component
public class UomMapper {

	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public UomVo EntityToVo(UomEntity dto) {
		UomVo vo = new UomVo();
		vo.setId(dto.getId());
		vo.setUomName(dto.getUomName());
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<UomVo> EntityToVo(List<UomEntity> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public UomEntity VoToEntity(UomVo vo) {
		UomEntity dto = new UomEntity();
		dto.setId(vo.getId());
		dto.setUomName(vo.getUomName());
		return dto;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<UomEntity> VoToEntity(List<UomVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
