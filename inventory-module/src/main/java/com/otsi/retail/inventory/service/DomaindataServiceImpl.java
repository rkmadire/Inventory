package com.otsi.retail.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.DomainDataMapper;
import com.otsi.retail.inventory.model.Domaindata;
import com.otsi.retail.inventory.repo.DomainDataRepo;
import com.otsi.retail.inventory.vo.DomainDataVo;

@Component
public class DomaindataServiceImpl implements DomainDataService {

	private Logger log = LoggerFactory.getLogger(DomaindataServiceImpl.class);

	@Autowired
	private DomainDataRepo domainDataRepo;

	@Autowired
	private DomainDataMapper domainMapper;

	@Override
	public DomainDataVo saveDomainData(DomainDataVo vo) {
		log.debug("debugging saveDomainData:" + vo);
		Domaindata data = domainMapper.VoToEntity(vo);
		Domaindata domainSave = domainDataRepo.save(data);
		DomainDataVo domain = domainMapper.EntityToVo(domainSave);
		log.warn("we are checking if domain data is saved...");
		log.info("after saving domain details:" + vo.toString());
		return domain;
	}

	@Override
	public Optional<Domaindata> getDomainDataById(Long domainDataId) {
		log.debug("debugging getDomainDataById:" + domainDataId);
		Optional<Domaindata> vo = domainDataRepo.findById(domainDataId);
		if (!(vo.isPresent())) {
			log.error("domain  record is not found");
			throw new RecordNotFoundException("domain record is not found");
		}
		log.warn("we are checking if domain data is fetching by id...");
		log.info("after fetching domain details:" + vo.toString());
		return vo;

	}

	@Override
	public List<DomainDataVo> getAllDomainData() {
		log.debug("debugging getAllDomainData");
		List<DomainDataVo> domainVos = new ArrayList<>();
		List<Domaindata> domainDatas = domainDataRepo.findAll();
		domainDatas.stream().forEach(domain -> {
			DomainDataVo domainVo = domainMapper.EntityToVo(domain);
			domainVos.add(domainVo);
		});
		log.warn("we are checking if all domains are fetching...");
		log.info("after fetching all domain details:" + domainVos.toString());
		return domainVos;

	}

	@Override
	public String updateDomainData(DomainDataVo domainDataVo) {
		log.debug("debugging updateDomainData:" + domainDataVo);
		Domaindata domain = domainDataRepo.findByDomainDataId(domainDataVo.getDomainDataId());
		if (domain == null) {
			throw new RecordNotFoundException("domain data is  not found with id: " + domainDataVo.getDomainDataId());
		}
		Domaindata domainData = domainMapper.VoToEntity(domainDataVo);
		domainData.setDomainDataId(domainDataVo.getDomainDataId());
		domainData.setLastmodified(domainDataVo.getLastmodified());
		Domaindata domainUpdate = domainDataRepo.save(domainData);
		// DomainDataVo domainUpdate = domainMapper.EntityToVo(domainData);
		log.warn("we are checking if domain is updated...");
		log.info("after updating domain details:" + domainUpdate);
		return "domain data updated successfully";
	}

	@Override
	public String deleteDomain(Long domainDataId) {
		log.debug("debugging deleteDomain:" + domainDataId);
		Optional<Domaindata> domain = domainDataRepo.findById(domainDataId);
		if (!(domain.isPresent())) {
			throw new RecordNotFoundException("domain not found with id: " + domainDataId);
		}
		domainDataRepo.delete(domain.get());
		log.warn("we are checking if domain is deleted...");
		log.info("after deleting domain details:" + domainDataId);
		return "domain data deleted succesfully: " + domainDataId;
	}

}
