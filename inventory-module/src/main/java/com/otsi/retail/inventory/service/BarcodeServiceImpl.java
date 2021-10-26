package com.otsi.retail.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otsi.retail.inventory.config.Config;
import com.otsi.retail.inventory.exceptions.DuplicateRecordException;
import com.otsi.retail.inventory.exceptions.InvalidDataException;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.exceptions.ServiceDownException;
import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.mapper.BarcodeMapper;
import com.otsi.retail.inventory.mapper.ProductItemMapper;
import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.repo.BarcodeRepo;
import com.otsi.retail.inventory.repo.ProductItemRepo;
import com.otsi.retail.inventory.vo.BarcodeVo;
import com.otsi.retail.inventory.vo.CatalogVo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class BarcodeServiceImpl implements BarcodeService {

	private Logger log = LoggerFactory.getLogger(BarcodeServiceImpl.class);

	@Autowired
	private BarcodeRepo barcodeRepo;

	@Autowired
	private BarcodeMapper barcodemapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProductItemRepo productItemRepo;

	@Autowired
	private ProductItemMapper productItemMapper;

	@Autowired
	private Config config;

	@Override
	//@CircuitBreaker(name = "catalog", fallbackMethod = "getCatalogsFromCatalog")
	public String createBarcode(BarcodeVo vo) {
		log.debug("debugging createBarcode:" + vo);
		if (vo.getDefaultCategoryId() == null) {
			throw new InvalidDataException("please give valid data");
		}
		if (barcodeRepo.existsByBarcode(vo.getBarcode())) {
			throw new DuplicateRecordException("barcode is already exists:" + vo.getBarcode());
		}

		List<CatalogVo> resVo = vo.getDefaultCategoryId();
		List<CatalogVo> catalogsFromCatalog = getCatalogsFromCatalog(resVo.get(0).getId());
		if (catalogsFromCatalog == null) {
			throw new RecordNotFoundException("catalog record is not found");

		}

		/*
		 * ResponseEntity<?> catalogResponse =
		 * restTemplate.exchange(config.getGetCatagoriesUrl() + "?id=" + resVo.getId(),
		 * HttpMethod.GET, null, GateWayResponse.class);
		 * System.out.println("Received Request to getCatalog categories:" +
		 * catalogResponse); ObjectMapper mapper = new ObjectMapper();
		 * GateWayResponse<?> gatewayResponse =
		 * mapper.convertValue(catalogResponse.getBody(), GateWayResponse.class);
		 * 
		 * CatalogVo[] catalogVo = mapper.convertValue(gatewayResponse.getResult(),
		 * CatalogVo[].class);
		 * 
		 * if (catalogVo.getClass() == null) { throw new
		 * RecordNotFoundException("catalog category id is not found"); }
		 */
		Barcode barcode = new Barcode();
		barcode.setDefaultCategoryId(resVo.get(0).getId());
		barcode.setBarcode(vo.getBarcode());
		barcode.setAttr1(vo.getAttr1());
		barcode.setAttr2(vo.getAttr2());
		barcode.setAttr3(vo.getAttr3());
		barcode.setCreationDate(LocalDate.now());
		barcode.setLastModified(LocalDate.now());
		barcodeRepo.save(barcode);

		log.warn("we are testing if barcode is saved...");
		log.info("after saving barcode details:" + barcode);
		return "barcode created successfully:" + barcode;
	}
	
	public List<CatalogVo> getCatalogsFromCatalog(List<CatalogVo> vo) {
		log.error("Invocie details service down");
		throw new ServiceDownException("Catalog details are down");
	}

	@Override
	public BarcodeVo getBarcode(String barcode) {
		log.debug("debugging getBarcode:" + barcode);
		Barcode dto = barcodeRepo.findByBarcode(barcode);
		if (dto == null) {
			throw new RecordNotFoundException("barcode record is not found");
		}

		BarcodeVo vo = barcodemapper.EntityToVo(dto);

		/*
		 * List<CatalogVo> catalogsFromCatalog =
		 * getCatalogsFromCatalog(vo.getDefaultCategoryId().get(0).getId());
		 * vo.setDefaultCategoryId(catalogsFromCatalog);
		 */

		log.warn("we are testing if barcode is fetching...");
		log.info("after fetching barcode details:");
		return vo;
	}

	@Override
	public List<BarcodeVo> getAllBarcodes(BarcodeVo vo) {
		log.debug("debugging getAllBarcodes");
		List<Barcode> barcodeDetails = new ArrayList<>();

		/*
		 * using dates
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() == null) {
			barcodeDetails = barcodeRepo.findByCreationDateBetweenOrderByLastModifiedAsc(vo.getFromDate(),
					vo.getToDate());

			if (barcodeDetails.isEmpty()) {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		/*
		 * using dates and barcodeId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() != null) {
			Optional<Barcode> barOpt = barcodeRepo.findByBarcodeId(vo.getBarcodeId());
			if (barOpt.isPresent()) {
				barcodeDetails = barcodeRepo.findByCreationDateBetweenAndBarcodeIdOrderByLastModifiedAsc(
						vo.getFromDate(), vo.getToDate(), vo.getBarcodeId());
			} else {
				log.error("No record found with given BarcodeId");
				throw new RecordNotFoundException("No record found with given BarcodeId");
			}

		}

		List<BarcodeVo> barcodeList = barcodemapper.EntityToVo(barcodeDetails);
		log.warn("we are testing if all barcodes are fetching...");
		log.info("after fetching all barcode details:");
		return barcodeList;

	}

	@Override
	public String deleteBarcode(Long barcodeId) {
		log.debug("debugging deleteBarcode:" + barcodeId);
		Optional<Barcode> barcode = barcodeRepo.findById(barcodeId);
		if (!(barcode.isPresent())) {
			throw new RecordNotFoundException("barcode not found with id: " + barcodeId);
		}
		barcodeRepo.delete(barcode.get());
		log.warn("we are testing if barcode is deleted...");
		log.info("after deleting  barcode details:" + barcodeId);
		return "barcode deleted succesfully: " + barcodeId;
	}

	@Override
	public List<CatalogVo> getCatalogsFromCatalog(Long id) {

		ResponseEntity<?> catalogResponse = restTemplate.exchange(config.getGetCatagoriesUrl() + "?id=" + id,
				HttpMethod.GET, null, GateWayResponse.class);
		ObjectMapper mapper = new ObjectMapper();
		GateWayResponse<?> gatewayResponse = mapper.convertValue(catalogResponse.getBody(), GateWayResponse.class);
		List<CatalogVo> vo = mapper.convertValue(gatewayResponse.getResult(), new TypeReference<List<CatalogVo>>() {
		});
		return vo;
	}

}
