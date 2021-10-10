package com.otsi.retail.inventory.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.DomainDataMapper;
import com.otsi.retail.inventory.model.Domaindata;
import com.otsi.retail.inventory.repo.DomainDataRepo;
import com.otsi.retail.inventory.vo.DomaindataVo;

@Component
public class DomaindataServiceImpl implements DomainDataService {

	private Logger log = LoggerFactory.getLogger(DomaindataServiceImpl.class);

	@Autowired
	private DomainDataRepo domainDataRepo;

	@Autowired
	private DomainDataMapper domainMapper;

	@Override
	public DomaindataVo saveDomainData(DomaindataVo vo) {
		log.debug("debugging saveDomainData:" + vo);
		Domaindata data = domainMapper.VoToEntity(vo);
		Domaindata domainSave = domainDataRepo.save(data);
		DomaindataVo domain = domainMapper.EntityToVo(domainSave);
		log.warn("we are checking if domain data is saved...");
		log.info("after saving domain details:" + vo.toString());
		return domain;
	}

	@Override
	public Optional<Domaindata> getDomainDataById(Long domainDataId) {
		log.debug("debugging getDomainDataById:" + domainDataId);
		Optional<Domaindata> vo = domainDataRepo.findByDomainDataId(domainDataId);
		if (!(vo.isPresent())) {
			log.error("domain  record is not found");
			throw new RecordNotFoundException("domain record is not found");
		}
		log.warn("we are checking if domain data is fetching by id...");
		log.info("after fetching domain details:" + vo.toString());
		return vo;

	}

}
