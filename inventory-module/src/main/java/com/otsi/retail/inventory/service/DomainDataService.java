package com.otsi.retail.inventory.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.model.Domaindata;
import com.otsi.retail.inventory.vo.DomaindataVo;

@Service
public interface DomainDataService {
	
	
	DomaindataVo saveDomainData(DomaindataVo vo);


	Optional<Domaindata> getDomainDataById(Long domainDataId);
	

	

}
