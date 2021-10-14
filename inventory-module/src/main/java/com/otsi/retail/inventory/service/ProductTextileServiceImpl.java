package com.otsi.retail.inventory.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.InvalidDataException;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.ProductTextileMapper;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.repo.ProductTextileRepo;
import com.otsi.retail.inventory.vo.ProductTextileVo;

@Component
public class ProductTextileServiceImpl implements ProductTextileService {

	private Logger log = LoggerFactory.getLogger(ProductTextileServiceImpl.class);

	@Autowired
	private ProductTextileMapper productTextileMapper;

	@Autowired
	private ProductTextileRepo productTextileRepo;

	@Override
	public String saveProductTextile(ProductTextileVo textileVo) {
		log.debug("debugging saveProductTextile:" + textileVo);
		if (textileVo.getStore() == null) {
			throw new InvalidDataException("please give valid data");
		}
		ProductTextile textile = productTextileMapper.VoToEntity(textileVo);
		ProductTextile textileSave = productTextileRepo.save(textile);
		log.warn("we are checking if textile is saved...");
		log.info("after saving textile details:" + textileSave.toString());
		return "textile saved successfully:" + textileSave;
	}

	@Override
	public Optional<ProductTextile> getProductTextile(Long id) {
		log.debug("debugging createInventory:" + id);
		Optional<ProductTextile> textile = productTextileRepo.findById(id);
		if (!(textile.isPresent())) {
			throw new RecordNotFoundException("domain record is not found");
		}
		log.warn("we are checking if textile is fetching...");
		log.info("after fetching textile details:" + id);
		return textile;
	}

}
