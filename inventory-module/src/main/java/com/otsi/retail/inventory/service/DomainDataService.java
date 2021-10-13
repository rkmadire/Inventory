package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.model.Domaindata;
import com.otsi.retail.inventory.vo.DomainDataVo;

@Service
public interface DomainDataService {

	DomainDataVo saveDomainData(DomainDataVo vo);

	Optional<Domaindata> getDomainDataById(Long domainDataId);

	String updateDomainData(DomainDataVo domainDataVo);

	List<DomainDataVo> getAllDomainData();

	String deleteDomain(Long domainDataId);

}
