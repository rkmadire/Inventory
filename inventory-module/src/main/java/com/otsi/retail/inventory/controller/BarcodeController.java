package com.otsi.retail.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.service.BarcodeService;
import com.otsi.retail.inventory.vo.BarcodeVo;

@RestController
public class BarcodeController {

	private Logger log = LoggerFactory.getLogger(BarcodeController.class);

	@Autowired
	private BarcodeService barcodeService;

	@PostMapping("/createBarcode")
	public GateWayResponse<?> createBarcode(@RequestBody BarcodeVo vo) {
		log.info("Recieved request to createBarcode:" + vo);
		String barcodeSave = barcodeService.createBarcode(vo);
		return new GateWayResponse<>("Barcode created successfully", barcodeSave);

	}

	@GetMapping("/getBarcode")
	public GateWayResponse<?> getBarcode(@RequestParam String barcode) {
		log.info("Recieved request to getBarcode:" + barcode);
		BarcodeVo fetchBarcode = barcodeService.getBarcode(barcode);
		return new GateWayResponse<>("fetching Barcode details successfully with barcode", fetchBarcode);
	}

	@GetMapping("/getAllBarcodes")
	public GateWayResponse<?> getAllBarcodes() {
		log.info("Recieved request to getAllBarcodes");
		List<BarcodeVo> allBarcodes = barcodeService.getAllBarcodes();
		return new GateWayResponse<>("fetching all barcode details sucessfully", allBarcodes);

	}

	@DeleteMapping("/deleteBarcode")
	public GateWayResponse<?> deleteBarcode(@RequestParam("barcodeId") Long barcodeId) throws Exception {
		log.info("Recieved request to deleteBarcode:" + barcodeId);
		String deleteBarcode = barcodeService.deleteBarcode(barcodeId);
		return new GateWayResponse<>("barcode deleted successfully", deleteBarcode);

	}

	/*
	 * @GetMapping("/getCatalogsFromCatalog") public GateWayResponse<?>
	 * getCatalogsFromCatalog(Long id) { CatalogCategoriesVo vo =
	 * barcodeService.getCatalogsFromCatalog(id); return new
	 * GateWayResponse<>("fetching  name successfully from catalog catagories", vo);
	 * 
	 * }
	 */

}
