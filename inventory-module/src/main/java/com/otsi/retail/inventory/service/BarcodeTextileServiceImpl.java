package com.otsi.retail.inventory.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.BarcodeTextileMapper;
import com.otsi.retail.inventory.mapper.ProductTextileMapper;
import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.repo.BarcodeTextileRepo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;

@Component
public class BarcodeTextileServiceImpl implements BarcodeTextileService {

	private Logger log = LoggerFactory.getLogger(BarcodeTextileServiceImpl.class);

	@Autowired
	private BarcodeTextileMapper barcodeTextileMapper;

	@Autowired
	private BarcodeTextileRepo barcodeTextileRepo;

	@Autowired
	private ProductTextileMapper productTextileMapper;

	@Override
	public String saveBarcodeTextile(BarcodeTextileVo barcodeTextileVo) {
		log.debug("debugging saveBarcodeTextile:" + barcodeTextileVo);
		BarcodeTextile textile = barcodeTextileMapper.VoToEntity(barcodeTextileVo);
		BarcodeTextile textileSave = barcodeTextileRepo.save(textile);
		log.warn("we are checking if textile is saved...");
		log.info("after saving textile details:" + textileSave.toString());
		return "textile saved successfully:" + textileSave;
	}

	@Override
	public BarcodeTextileVo getBarcodeTextile(String barcode) {
		log.debug("debugging createInventory:" + barcode);
		Optional<BarcodeTextile> textile = barcodeTextileRepo.findByBarcode(barcode);
		if (!textile.isPresent()) {
			throw new RecordNotFoundException("domain record is not found");
		}
		
		BarcodeTextileVo vo = barcodeTextileMapper.EntityToVo(textile.get());
		vo.setProductTextile(productTextileMapper.EntityToVo(textile.get().getProductTextile()));
		log.warn("we are checking if textile is fetching...");
		return vo;
	}

}
