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

import com.otsi.retail.inventory.commons.NatureOfTransaction;
import com.otsi.retail.inventory.commons.ProductStatus;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.AdjustmentMapper;
import com.otsi.retail.inventory.mapper.BarcodeTextileMapper;
import com.otsi.retail.inventory.mapper.ProductTextileMapper;
import com.otsi.retail.inventory.mapper.ProductTransactionMapper;
import com.otsi.retail.inventory.model.Adjustments;
import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.model.ProductTransaction;
import com.otsi.retail.inventory.repo.AdjustmentRepo;
import com.otsi.retail.inventory.repo.BarcodeTextileRepo;
import com.otsi.retail.inventory.repo.ProductTextileRepo;
import com.otsi.retail.inventory.repo.ProductTransactionRepo;
import com.otsi.retail.inventory.vo.AdjustmentsVo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductItemVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.ProductTransactionVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;
import com.otsi.retail.inventory.vo.UpdateInventoryRequest;

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

	@Autowired
	private ProductTransactionRepo productTransactionRepo;

	@Autowired
	private AdjustmentRepo adjustmentRepo;

	@Autowired
	private ProductTransactionMapper productTransactionMapper;

	@Autowired
	private AdjustmentMapper adjustmentMapper;

	@Override
	public String addBarcodeTextile(BarcodeTextileVo textileVo) {
		log.debug("debugging saveProductTextile:" + textileVo);
		Random ran = new Random();
		BarcodeTextile barTextile = new BarcodeTextile();
		barTextile.setBarcodeTextileId(textileVo.getBarcodeTextileId());
		barTextile.setBarcode("BAR" + ran.nextInt());
		barTextile.setCreationDate(LocalDate.now());
		barTextile.setLastModified(LocalDate.now());
		barTextile.setDivision(textileVo.getDivision());
		barTextile.setSection(textileVo.getSection());
		barTextile.setSubSection(textileVo.getSubSection());
		barTextile.setCategory(textileVo.getCategory());
		barTextile.setBatchNo(textileVo.getBatchNo());
		barTextile.setColour(textileVo.getColour());
		BarcodeTextile barTextileSave = barcodeTextileRepo.save(barTextile);
		ProductTextile textile = new ProductTextile();
		textile.setProductTextileId(textileVo.getProductTextile().getProductTextileId());
		textile.setBarcodeTextile(barTextileSave);
		textile.setCostPrice(textileVo.getProductTextile().getCostPrice());
		textile.setUom(textileVo.getProductTextile().getUom());
		textile.setHsnMasterId(textileVo.getProductTextile().getHsnMasterId());
		textile.setEmpId(textileVo.getProductTextile().getEmpId());
		textile.setItemMrp(textileVo.getProductTextile().getItemMrp());
		textile.setItemCode(textileVo.getProductTextile().getItemCode());
		textile.setCreateForLocation(0);
		textile.setValueAdditionCp(0);
		textile.setStatus(ProductStatus.ENABLE);
		textile.setStoreId(textileVo.getProductTextile().getStoreId());
		textile.setCreatedAt(LocalDate.now());
		textile.setUpdatedAt(LocalDate.now());
		textile.setOriginalBarcodeCreatedAt(LocalDate.now());
		ProductTextile textileSave = productTextileRepo.save(textile);

		ProductTransaction prodTrans = new ProductTransaction();
		prodTrans.setBarcodeId(barTextileSave.getBarcodeTextileId());
		prodTrans.setStoreId(textileSave.getStoreId());
		prodTrans.setEffectingTableID(textileSave.getProductTextileId());
		prodTrans.setQuantity(textileVo.getProductTextile().getQty());
		prodTrans.setCreationDate(LocalDate.now());
		prodTrans.setLastModified(LocalDate.now());
		prodTrans.setNatureOfTransaction(NatureOfTransaction.PURCHASE.getName());
		prodTrans.setMasterFlag(true);
		prodTrans.setComment("newly inserted table");
		prodTrans.setEffectingTable("product textile table");
		ProductTransaction saveTrans = productTransactionRepo.save(prodTrans);
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
		Optional<BarcodeTextile> dto = barcodeTextileRepo.findByBarcodeTextileId(vo.getBarcodeTextileId());
		if (!dto.isPresent()) {
			log.error("Record Not Found");
			throw new RecordNotFoundException("barcode textile record not found");
		}
		Optional<ProductTextile> prodTxt = productTextileRepo
				.findByProductTextileId(dto.get().getProductTextile().getProductTextileId());
		if (!prodTxt.isPresent()) {
			log.error("Record Not Found");
			throw new RecordNotFoundException("product textile record not found");
		}
		BarcodeTextile barTextile1 = dto.get();
		ProductTextile textile1 = barTextile1.getProductTextile();
		textile1.setStatus(ProductStatus.DISABLE);
		productTextileRepo.save(textile1);

		Random ran = new Random();
		BarcodeTextile barTextile = new BarcodeTextile();
		barTextile.setBarcode(
				"REBAR/" + LocalDate.now().getYear() + LocalDate.now().getDayOfMonth() + "/" + ran.nextInt());
		barTextile.setCreationDate(LocalDate.now());
		barTextile.setLastModified(LocalDate.now());
		barTextile.setBatchNo(vo.getBatchNo());
		barTextile.setDivision(vo.getDivision());
		barTextile.setSection(vo.getSection());
		barTextile.setSubSection(vo.getSubSection());
		barTextile.setColour(vo.getColour());
		barTextile.setCategory(vo.getCategory());
		BarcodeTextile barTextileSave = barcodeTextileRepo.save(barTextile);
		ProductTextile textile = new ProductTextile();
		textile.setParentBarcode(barTextile1.getBarcode());
		textile.setStatus(ProductStatus.ENABLE);
		textile.setBarcodeTextile(barTextileSave);
		textile.setCostPrice(vo.getProductTextile().getCostPrice());
		textile.setUom(vo.getProductTextile().getUom());
		textile.setHsnMasterId(vo.getProductTextile().getHsnMasterId());
		textile.setEmpId(vo.getProductTextile().getEmpId());
		textile.setItemMrp(vo.getProductTextile().getItemMrp());
		textile.setItemCode(vo.getProductTextile().getItemCode());
		textile.setCreateForLocation(vo.getProductTextile().getCreateForLocation());
		textile.setValueAdditionCp(vo.getProductTextile().getValueAdditionCp());
		textile.setStoreId(vo.getProductTextile().getStoreId());
		textile.setCreatedAt(LocalDate.now());
		textile.setUpdatedAt(LocalDate.now());
		textile.setOriginalBarcodeCreatedAt(LocalDate.now());
		ProductTextile textileSave = productTextileRepo.save(textile);
		ProductTransaction transact = productTransactionRepo.findByBarcodeId(vo.getBarcodeTextileId());
		transact.setMasterFlag(false);
		productTransactionRepo.save(transact);
		Adjustments ad = new Adjustments();
		ad.setCreatedBy(textileSave.getEmpId());
		ad.setCreationDate(LocalDate.now());
		ad.setCurrentBarcodeId(barTextileSave.getBarcode());
		ad.setToBeBarcodeId(barTextile1.getBarcode());
		ad.setLastModifiedDate(LocalDate.now());
		ad.setComments("rebar");
		Adjustments audSave = adjustmentRepo.save(ad);
		ProductTransaction prodTrans = new ProductTransaction();
		prodTrans.setBarcodeId(barTextileSave.getBarcodeTextileId());
		prodTrans.setStoreId(textileSave.getStoreId());
		prodTrans.setEffectingTableID(audSave.getAdjustmentId());
		prodTrans.setQuantity(vo.getProductTextile().getQty());
		prodTrans.setNatureOfTransaction(NatureOfTransaction.REBARPARENT.getName());
		prodTrans.setCreationDate(LocalDate.now());
		prodTrans.setLastModified(LocalDate.now());
		prodTrans.setMasterFlag(true);
		prodTrans.setComment("Adjustments");
		prodTrans.setEffectingTable("Adjustments");
		ProductTransaction saveTrans = productTransactionRepo.save(prodTrans);

		return "Rebarcoding textile updated successfully:" + barTextileSave.getBarcodeTextileId();
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
		ProductTransaction transact = productTransactionRepo.findByBarcodeId(vo.getBarcodeTextileId());
		vo.getProductTextile().setQty(transact.getQuantity());
		vo.getProductTextile().setValue(transact.getQuantity() * vo.getProductTextile().getCostPrice());
		log.warn("we are checking if textile is fetching...");
		return vo;
	}

	@Override
	public List<BarcodeTextileVo> getAllBarcodes(SearchFilterVo vo) {
		log.debug("debugging getAllBarcodes()");
		List<BarcodeTextile> barcodeDetails = new ArrayList<>();

		if (vo.getFromDate() != null && vo.getToDate() != null && (vo.getBarcode() == null || vo.getBarcode() == "")) {
			ProductStatus status = ProductStatus.ENABLE;

			barcodeDetails = barcodeTextileRepo.findByCreationDateBetweenAndProductTextileStatusOrderByLastModifiedAsc(
					vo.getFromDate(), vo.getToDate(), status);

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
		 * using dates with barcode
		 */

		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcode() != null) {
			BarcodeTextile barOpt = barcodeTextileRepo.findByBarcode(vo.getBarcode());
			if (barOpt != null) {
				barcodeDetails = barcodeTextileRepo.findByCreationDateBetweenAndBarcodeOrderByLastModifiedAsc(
						vo.getFromDate(), vo.getToDate(), vo.getBarcode());
			} else {
				log.error("No record found with given barcode");
				throw new RecordNotFoundException("No record found with given barcode");
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
		/*
		 * using barcode
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && (!vo.getBarcode().isEmpty())) {
			BarcodeTextile textile = barcodeTextileRepo.findByBarcode(vo.getBarcode());
			if (textile == null) {
				throw new RecordNotFoundException("textile record is not found");
			}

			barcodeDetails.add(textile);
			List<BarcodeTextileVo> barcodeList = barcodeTextileMapper.EntityToVo(barcodeDetails);
			barcodeList.stream().forEach(v -> {
				ProductTransaction transact = productTransactionRepo.findByBarcodeId(v.getBarcodeTextileId());
				v.getProductTextile().setQty(transact.getQuantity());
				v.getProductTextile().setValue(transact.getQuantity() * v.getProductTextile().getCostPrice());
			});
			return barcodeList;
		}

		/*
		 * values with empty string
		 */
		else if ((vo.getFromDate() == null) && (vo.getToDate() == null) && (vo.getBarcode() == "")) {
			ProductStatus status = ProductStatus.ENABLE;

			List<BarcodeTextile> barcodeTextileList = barcodeTextileRepo.findByProductTextileStatus(status);

			List<BarcodeTextileVo> barcodeList = barcodeTextileMapper.EntityToVo(barcodeTextileList);
			barcodeList.stream().forEach(v -> {
				ProductTransaction transact = productTransactionRepo.findByBarcodeId(v.getBarcodeTextileId());
				/*
				 * ProductTextile textileStat = productTextileRepo.findByStatus(status);
				 * v.setProductTextile(productTextileMapper.EntityToVo(textileStat));
				 */
				v.getProductTextile().setQty(transact.getQuantity());
				v.getProductTextile().setValue(transact.getQuantity() * v.getProductTextile().getCostPrice());
			});
			return barcodeList;
		}

		List<BarcodeTextileVo> barcodeList = barcodeTextileMapper.EntityToVo(barcodeDetails);
		barcodeList.stream().forEach(v -> {

			ProductTransaction transact = productTransactionRepo.findByBarcodeId(v.getBarcodeTextileId());
			v.getProductTextile().setQty(transact.getQuantity());

			v.getProductTextile().setValue(transact.getQuantity() * v.getProductTextile().getCostPrice());
		});

		log.warn("we are checking if barcode textile is fetching...");
		log.info("fetching all barcode textile details");
		return barcodeList;
	}

	@Override
	public String inventoryUpdateForTextile(List<UpdateInventoryRequest> request) {
		BarcodeTextile barcodeDetails = barcodeTextileRepo.findByBarcode(request.get(0).getBarcode());
		ProductTransaction transact = productTransactionRepo.findByBarcodeId(barcodeDetails.getBarcodeTextileId());
		transact.setMasterFlag(false);
		transact.setQuantity(Math.abs(request.get(0).getQty() - transact.getQuantity()));
		productTransactionRepo.save(transact);
		ProductTransaction prodTrans = new ProductTransaction();
		prodTrans.setBarcodeId(barcodeDetails.getBarcodeTextileId());
		prodTrans.setEffectingTableID(request.get(0).getLineItemId());
		prodTrans.setQuantity(request.get(0).getQty());
		prodTrans.setStoreId(request.get(0).getStoreId());
		prodTrans.setNatureOfTransaction(NatureOfTransaction.SALE.getName());
		prodTrans.setCreationDate(LocalDate.now());
		prodTrans.setLastModified(LocalDate.now());
		prodTrans.setMasterFlag(true);
		prodTrans.setComment("sale");
		prodTrans.setEffectingTable("order table");
		ProductTransaction saveTrans = productTransactionRepo.save(prodTrans);
		return "updated inventory for textile from newsale successfully:" + saveTrans.getProductTransactionId();
	}

	@Override
	public List<AdjustmentsVo> getAllAdjustments(AdjustmentsVo vo) {
		log.info("Received request to getAllAdjustments:" + vo);
		List<Adjustments> adjustmentDetails = new ArrayList<>();

		/*
		 * using dates
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && (vo.getCurrentBarcodeId() == "")) {
			adjustmentDetails = adjustmentRepo.findByCreationDateBetweenOrderByLastModifiedDateAsc(vo.getFromDate(),
					vo.getToDate());

			if (adjustmentDetails.isEmpty()) {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		/*
		 * using dates and currentBarcodeId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getCurrentBarcodeId() != null) {
			Adjustments adjustOpt = adjustmentRepo.findByCurrentBarcodeId(vo.getCurrentBarcodeId());
			if (adjustOpt != null) {
				adjustmentDetails = adjustmentRepo
						.findByCreationDateBetweenAndCurrentBarcodeIdOrderByLastModifiedDateAsc(vo.getFromDate(),
								vo.getToDate(), vo.getCurrentBarcodeId());
			} else {
				log.error("No record found with given adjustmentId");
				throw new RecordNotFoundException("No record found with given adjustmentId");
			}

		}

		/*
		 * values with empty string
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && vo.getCurrentBarcodeId() == "") {
			List<Adjustments> adjustDetails1 = adjustmentRepo.findAll();
			List<AdjustmentsVo> adjustDetails = adjustmentMapper.EntityToVo(adjustDetails1);
			return adjustDetails;
		}
		/*
		 * using currentBarcodeId
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && vo.getCurrentBarcodeId() != null) {
			Adjustments adjustOpt = adjustmentRepo.findByCurrentBarcodeId(vo.getCurrentBarcodeId());
			if (adjustOpt == null) {
				throw new RecordNotFoundException("adjustment record is not found");

			} else {

				adjustmentDetails.add(adjustOpt);
				List<AdjustmentsVo> adjustList = adjustmentMapper.EntityToVo(adjustmentDetails);
				return adjustList;

			}
		} else {
			List<Adjustments> adjustDetails1 = adjustmentRepo.findAll();
			List<AdjustmentsVo> adjustDetails = adjustmentMapper.EntityToVo(adjustDetails1);
			return adjustDetails;
		}

		List<AdjustmentsVo> adjustmentList = adjustmentMapper.EntityToVo(adjustmentDetails);
		log.warn("we are checking if barcode textile is fetching...");
		log.info("fetching all barcode textile details");
		return adjustmentList;

	}
}
