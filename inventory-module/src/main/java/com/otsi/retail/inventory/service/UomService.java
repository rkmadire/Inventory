package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.model.UomEntity;
import com.otsi.retail.inventory.vo.UomVo;

@Service
public interface UomService {

	UomVo saveUom(UomVo vo);

	Optional<UomEntity> getUom(Long id);

	List<UomVo> getAllUom();

	String updateUom(UomVo uomVo);

	String deleteUom(Long id);

}
