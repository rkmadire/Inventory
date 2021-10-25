package com.otsi.retail.inventory.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.InvalidDataException;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.BarcodeTextileMapper;
import com.otsi.retail.inventory.mapper.ProductTextileMapper;
import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.model.ProductInventory;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.repo.BarcodeTextileRepo;
import com.otsi.retail.inventory.repo.ProductTextileRepo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
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
		if (textileVo.getStore() == null || textileVo.getBarcodeTextile() == null) {
			throw new InvalidDataException("please give valid data");
		}
		ProductTextile textile = productTextileMapper.VoToEntity(textileVo);
		ProductTextile textileSave = productTextileRepo.save(textile);
		ProductTextileVo prod=productTextileMapper.EntityToVo(textileSave);

		log.warn("we are checking if textile is saved...");
		log.info("after saving textile details");
		return "textile saved successfully";
	}

	@Override
	public ProductTextileVo getProductTextile(Long productTextileId) {
		log.debug("debugging createInventory:" + productTextileId);
		Optional<ProductTextile> textile = productTextileRepo.findById(productTextileId);
		if (!(textile.isPresent())) {
			throw new RecordNotFoundException("domain record is not found");
		}
		ProductTextileVo vo = productTextileMapper.EntityToVo(textile.get());
		log.warn("we are checking if textile is fetching...");
		log.info("after fetching textile details:" + productTextileId);
		return vo;
	}

}
