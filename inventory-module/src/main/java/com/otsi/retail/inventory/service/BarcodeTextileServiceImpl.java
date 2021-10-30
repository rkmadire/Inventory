package com.otsi.retail.inventory.service;

import java.util.ArrayList;
import java.util.List;
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
		BarcodeTextile textile = barcodeTextileRepo.findByBarcode(barcode);
		if (textile == null) {
			throw new RecordNotFoundException("domain record is not found");
		}

		BarcodeTextileVo vo = barcodeTextileMapper.EntityToVo(textile);
		vo.setProductTextile(productTextileMapper.EntityToVo(textile.getProductTextile()));
		log.warn("we are checking if textile is fetching...");
		return vo;
	}

	@Override
	public List<BarcodeTextileVo> getAllBarcodes(BarcodeTextileVo vo) {
		log.debug("debugging getAllBarcodes()");
		List<BarcodeTextile> barcodeDetails = new ArrayList<>();

		/*
		 * using dates
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeTextileId() == null) {
			barcodeDetails = barcodeTextileRepo.findByCreationDateBetweenOrderByLastModifiedAsc(vo.getFromDate(),
					vo.getToDate());

			if (barcodeDetails.isEmpty()) {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		/*
		 * using dates and barcodeTextileId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeTextileId() != null) {
			Optional<BarcodeTextile> barOpt = barcodeTextileRepo.findByBarcodeTextileId(vo.getBarcodeTextileId());
			if (barOpt.isPresent()) {
				barcodeDetails = barcodeTextileRepo.findByCreationDateBetweenAndBarcodeTextileIdOrderByLastModifiedAsc(
						vo.getFromDate(), vo.getToDate(), vo.getBarcodeTextileId());
			} else {
				log.error("No record found with given barcodeTextileId");
				throw new RecordNotFoundException("No record found with given barcodeTextileId");
			}

		}

		/*
		 * using dates and barcode
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcode() != null) {
			BarcodeTextile barOpt = barcodeTextileRepo.findByBarcode(vo.getBarcode());
			if (barOpt != null) {
				barcodeDetails = barcodeTextileRepo.findByCreationDateBetweenAndBarcodeOrderByLastModifiedAsc(
						vo.getFromDate(), vo.getToDate(), vo.getBarcode());
			} else {
				log.error("No record found with given barcodeTextileId");
				throw new RecordNotFoundException("No record found with given barcodeTextileId");
			}

		}
		List<BarcodeTextileVo> barcodeList = barcodeTextileMapper.EntityToVo(barcodeDetails);
		log.warn("we are checking if barcode textile is fetching...");
		log.info("fetching all barcode textile details");
		return barcodeList;
	}

}
