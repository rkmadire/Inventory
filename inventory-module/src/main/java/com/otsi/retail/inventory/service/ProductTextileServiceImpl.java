package com.otsi.retail.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.InvalidDataException;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.BarcodeTextileMapper;
import com.otsi.retail.inventory.mapper.ProductTextileMapper;
import com.otsi.retail.inventory.mapper.StoreMapper;
import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.model.Store;
import com.otsi.retail.inventory.repo.BarcodeTextileRepo;
import com.otsi.retail.inventory.repo.ProductTextileRepo;
import com.otsi.retail.inventory.repo.StoreRepo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductItemVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;

@Component
public class ProductTextileServiceImpl implements ProductTextileService {

	private Logger log = LoggerFactory.getLogger(ProductTextileServiceImpl.class);

	@Autowired
	private BarcodeTextileMapper barcodeTextileMapper;

	@Autowired
	private ProductTextileMapper productTextileMapper;

	@Autowired
	private ProductTextileRepo productTextileRepo;

	@Autowired
	private BarcodeTextileRepo barcodeTextileRepo;

	@Override
	public String addBarcodeTextile(BarcodeTextileVo textileVo) {
		log.debug("debugging saveProductTextile:" + textileVo);
		Random ran = new Random();
		BarcodeTextile barTextile = new BarcodeTextile();
		barTextile.setBarcodeTextileId(textileVo.getBarcodeTextileId());
		barTextile.setBarcode("BAR" + ran.nextInt());
		barTextile.setCreationDate(LocalDate.now());
		barTextile.setLastModified(LocalDate.now());
		BarcodeTextile barTextileSave = barcodeTextileRepo.save(barTextile);
		ProductTextile textile = new ProductTextile();
		textile.setProductTextileId(textileVo.getProductTextile().getProductTextileId());
		textile.setBarcodeTextile(barTextileSave);
		textile.setCostPrice(textileVo.getProductTextile().getCostPrice());
		textile.setQty(1);
		textile.setUom("units");
		textile.setHsnMasterId(0);
		textile.setEmpId(textileVo.getProductTextile().getEmpId());
		textile.setItemMrp(textileVo.getProductTextile().getItemMrp());
		textile.setItemCode(textileVo.getProductTextile().getItemCode());
		textile.setCreateForLocation(0);
		textile.setValueAdditionCp(0);
		textile.setStoreId(textileVo.getProductTextile().getStoreId());
		textile.setCreatedAt(LocalDate.now());
		textile.setUpdatedAt(LocalDate.now());
		textile.setOriginalBarcodeCreatedAt(LocalDate.now());
		ProductTextile textileSave = productTextileRepo.save(textile);

		log.warn("we are checking if textile is saved...");
		log.info("after saving textile details");
		return "barcode textile saved successfully:" + barTextileSave.getBarcodeTextileId();
	}

	@Override
	public String incrementQty(BarcodeTextileVo vo) {
		int qty = vo.getProductTextile().getQty();
		for (int i = 1; i <= qty; i++) {
			addBarcodeTextile(vo);
		}
		return "barcode textile saved successfully";
	}

	@Override
	public ProductTextileVo getProductTextile(Long productTextileId) {
		log.debug("debugging createInventory:" + productTextileId);
		Optional<ProductTextile> textile = productTextileRepo.findById(productTextileId);
		if (!(textile.isPresent())) {
			throw new RecordNotFoundException("domain record is not found");
		}
		ProductTextileVo vo = productTextileMapper.EntityToVo(textile.get());
		vo.setBarcodeTextileId(
				barcodeTextileMapper.EntityToVo(textile.get().getBarcodeTextile()).getBarcodeTextileId());
		log.warn("we are checking if textile is fetching...");
		log.info("after fetching textile details:" + productTextileId);
		return vo;
	}

	@Override
	public String updateBarcodeTextile(BarcodeTextileVo vo) {
		log.debug(" debugging updateBarcode:" + vo);
		if(vo.getProductTextile().getProductTextileId()==null) {
			throw new RecordNotFoundException("product textileId textile record not found");
		}
		Optional<BarcodeTextile> dto = barcodeTextileRepo.findByBarcodeTextileId(vo.getBarcodeTextileId());
		if (!dto.isPresent()) {
			log.error("Record Not Found");
			throw new RecordNotFoundException("barcode textile record not found");
		}
		if (dto.get().getProductTextile().getProductTextileId() != vo.getProductTextile().getProductTextileId()
				|| dto.get().getBarcodeTextileId() != vo.getBarcodeTextileId()) {
			throw new InvalidDataException("productTextileId record not found/barcodeTextileId is incorrect");
		}
		BarcodeTextile barTextile = dto.get();
		barTextile.setBarcode(vo.getBarcode());
		barTextile.setCreationDate(LocalDate.now());
		barTextile.setLastModified(LocalDate.now());
		BarcodeTextile barTextileSave = barcodeTextileRepo.save(barTextile);
		ProductTextile textile = new ProductTextile();
		textile.setProductTextileId(vo.getProductTextile().getProductTextileId());
		textile.setBarcodeTextile(barTextileSave);
		textile.setCostPrice(vo.getProductTextile().getCostPrice());
		textile.setUom(vo.getProductTextile().getUom());
		textile.setHsnMasterId(0);
		textile.setCreateForLocation(0);
		textile.setItemMrp(vo.getProductTextile().getItemMrp());
		textile.setItemCode(vo.getProductTextile().getItemCode());
		textile.setCostPrice(vo.getProductTextile().getCostPrice());
		textile.setEmpId(vo.getProductTextile().getEmpId());
		textile.setItemMrp(vo.getProductTextile().getItemMrp());
		textile.setValueAdditionCp(0);
		textile.setStoreId(vo.getProductTextile().getStoreId());
		textile.setUpdatedAt(LocalDate.now());
		textile.setOriginalBarcodeCreatedAt(LocalDate.now());
		ProductTextile textileSave = productTextileRepo.save(textile);
		return "barcode textile updated successfully:" + barTextileSave.getBarcodeTextileId();
	}

	@Override
	public String deleteBarcodeTextile(Long barcodeTextileId) {
		log.debug(" debugging deleteBarcodeTextile:" + barcodeTextileId);
		Optional<BarcodeTextile> barOpt = barcodeTextileRepo.findByBarcodeTextileId(barcodeTextileId);
		if (!barOpt.isPresent()) {
			log.error("barcode textile details not found with id");
			throw new RecordNotFoundException("barcode textile details not found with id: " + barcodeTextileId);
		}
		Optional<ProductTextile> prodOpt = productTextileRepo
				.findByProductTextileId(barOpt.get().getProductTextile().getProductTextileId());
		if (!prodOpt.isPresent()) {
			log.error("product textile details not found with id");
			throw new RecordNotFoundException(
					"product textile details not found with id: " + prodOpt.get().getProductTextileId());
		}
		productTextileRepo.delete(prodOpt.get());
		barcodeTextileRepo.delete(prodOpt.get().getBarcodeTextile());
		log.warn("we are checking if barcode is deleted based on id...");
		log.info("deleted barcode textile succesfully:" + barcodeTextileId);
		return "deleted barcode textile successfully with id:" + barcodeTextileId;

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
	public List<BarcodeTextileVo> getAllBarcodes(SearchFilterVo vo) {
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
		 * using empId
		 */
		else if (vo.getEmpId() != null) {

			Optional<ProductTextile> prodOpt = productTextileRepo.findByEmpId(vo.getEmpId());
			if (prodOpt.isPresent()) {
				barcodeDetails = barcodeTextileRepo.findByProductTextileEmpId(prodOpt.get().getEmpId());
			} else {
				log.error("No record found with given empId");
				throw new RecordNotFoundException("No record found with given empId");
			}

		}
		/*
		 * using itemMrp< and itemMrp>
		 */
		else if (vo.getItemMrpLessThan() != 0 && vo.getItemMrpGreaterThan() != 0) {
			List<ProductTextile> prodOpt = productTextileRepo.findByItemMrpBetween(vo.getItemMrpLessThan(),
					vo.getItemMrpGreaterThan());
			List<Long> bars = prodOpt.stream().map(s -> s.getBarcodeTextile().getBarcodeTextileId())
					.collect(Collectors.toList());

			barcodeDetails = barcodeTextileRepo.findByBarcodeTextileIdIn(bars);

			if (barcodeDetails.isEmpty()) {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		/*
		 * using barcodeStore(storeId)
		 */
		else if (vo.getStoreId() != null) {
			Optional<ProductTextile> prodOpt = productTextileRepo.findByStoreId(vo.getStoreId());
			if (prodOpt.isPresent()) {
				barcodeDetails = barcodeTextileRepo.findByProductTextileStoreId(vo.getStoreId());
			} else {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		else {
			List<BarcodeTextile> prodItemDetails1 = barcodeTextileRepo.findAll();
			List<BarcodeTextileVo> productList = barcodeTextileMapper.EntityToVo(prodItemDetails1);
			return productList;
		}

		List<BarcodeTextileVo> barcodeList = barcodeTextileMapper.EntityToVo(barcodeDetails);
		log.warn("we are checking if barcode textile is fetching...");
		log.info("fetching all barcode textile details");
		return barcodeList;
	}

	@Override
	public List<BarcodeTextileVo> getAllBarcodes(List<String> barcode) {
		List<BarcodeTextile> barcodeDetails = barcodeTextileRepo.findByBarcodeIn(barcode);

		if (barcodeDetails.isEmpty()) {
			throw new RecordNotFoundException("Barcode with number " + barcode + " is not exists");
		} else {
			List<BarcodeTextileVo> vo = barcodeTextileMapper.EntityToVo(barcodeDetails);
			return vo;
		}
	}

}
