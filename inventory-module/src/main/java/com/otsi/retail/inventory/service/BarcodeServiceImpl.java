package com.otsi.retail.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otsi.retail.inventory.config.Config;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.mapper.BarcodeMapper;
import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.repo.BarcodeRepo;
import com.otsi.retail.inventory.vo.BarcodeVo;
import com.otsi.retail.inventory.vo.CatalogVo;

@Component
public class BarcodeServiceImpl implements BarcodeService {

	@Autowired
	private BarcodeRepo barcodeRepo;

	@Autowired
	private BarcodeMapper barcodemapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Config config;

	@Override
	public String createBarcode(BarcodeVo vo) {
		if (barcodeRepo.existsByBarcode(vo.getBarcode())) {
			throw new RuntimeException("barcode is already exists:" + vo.getBarcode());
		}
		// Barcode bar = barcodemapper.VoToEntity(vo);
		/*
		 * CatalogCategoriesVo catalogVo = new CatalogCategoriesVo();
		 * bar.setDefaultCategoryId(catalogVo.getId());
		 */

		CatalogVo resVo = vo.getDefaultCategoryId();
		ResponseEntity<?> catalogResponse = restTemplate.exchange(config.getGetCatagoriesUrl() + "?id=" + resVo.getId(),
				HttpMethod.GET, null, GateWayResponse.class);
		System.out.println("Received Request to getCatalog categories:" + catalogResponse);
		ObjectMapper mapper = new ObjectMapper();
		GateWayResponse<?> gatewayResponse = mapper.convertValue(catalogResponse.getBody(), GateWayResponse.class);

		CatalogVo[] catalogVo = mapper.convertValue(gatewayResponse.getResult(), CatalogVo[].class);

		if (catalogVo.getClass() == null) {
			throw new RecordNotFoundException("catalog category id is not found");
		}
		Barcode barcode = new Barcode();
		barcode.setDefaultCategoryId(resVo.getId());
		barcode.setBarcode(vo.getBarcode());
		barcodeRepo.save(barcode);
		// BarcodeVo barCreate=barcodemapper.EntityToVo();
		return "barcode created successfully:" + barcode;
	}

	@Override
	public Optional<Barcode> getBarcode(String barcode) {
		Optional<Barcode> vo = barcodeRepo.findByBarcode(barcode);
		if (!(vo.isPresent())) {
			throw new RecordNotFoundException("barcode record is not found");
		}

		return vo;
	}

	@Override
	public List<BarcodeVo> getAllBarcodes() {
		List<BarcodeVo> barcodeVos = new ArrayList<>();
		List<Barcode> barcodes = barcodeRepo.findAll();
		barcodes.stream().forEach(barcode -> {
			BarcodeVo barcodeVo = barcodemapper.EntityToVo(barcode);
			barcodeVos.add(barcodeVo);
		});
		return barcodeVos;

	}

	@Override
	public String updateBarcode(Long barcodeId, BarcodeVo barcodeVo) {
		Barcode bar = barcodeRepo.getByBarcodeId(barcodeId);
		if (bar == null) {
			throw new RuntimeException("barcode not found with id: " + barcodeId);
		}
		Barcode barcode = barcodemapper.VoToEntity(barcodeVo);
		barcodeRepo.save(barcode);
		BarcodeVo barcodeUpdate = barcodemapper.EntityToVo(barcode);
		return "barcode updated successfully";
	}

	@Override
	public String deleteBarcode(Long barcodeId) {
		Optional<Barcode> barcode = barcodeRepo.findById(barcodeId);
		if (!(barcode.isPresent())) {
			throw new RuntimeException("barcode not found with id: " + barcodeId);
		}
		barcodeRepo.delete(barcode.get());
		return "barcode deleted succesfully: " + barcodeId;
	}

	/*
	 * @Override public CatalogCategoriesVo getCatalogsFromCatalog(Long id) {
	 * ResponseEntity<?> catalogResponse =
	 * restTemplate.exchange(config.getGetCatagoriesUrl() + "?id=" + id,
	 * HttpMethod.GET, null, GateWayResponse.class); ObjectMapper mapper = new
	 * ObjectMapper(); GateWayResponse<?> gatewayResponse =
	 * mapper.convertValue(catalogResponse.getBody(), GateWayResponse.class);
	 * CatalogCategoriesVo vo = mapper.convertValue(gatewayResponse.getResult(), new
	 * TypeReference<CatalogCategoriesVo>() { }); return vo; }
	 */
}
