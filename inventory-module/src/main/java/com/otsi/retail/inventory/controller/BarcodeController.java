package com.otsi.retail.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.service.BarcodeService;
import com.otsi.retail.inventory.vo.BarcodeVo;

@RestController
public class BarcodeController {

	@Autowired
	private BarcodeService barcodeService;

	@PostMapping("/createBarcode")
	public GateWayResponse<?> createBarcode(@RequestBody BarcodeVo vo) {

		String barcodeSave = barcodeService.createBarcode(vo);
		return new GateWayResponse<>("Barcode created successfully", barcodeSave);

	}

	@GetMapping("/getBarcode")
	public GateWayResponse<?> getBarcode(@RequestParam String barcode) {

		Optional<Barcode> fetchBarcode = barcodeService.getBarcode(barcode);
		return new GateWayResponse<>("fetching Barcode details successfully with barcode", fetchBarcode);
	}

	@GetMapping("/getAllBarcodes")
	public GateWayResponse<?> getAllBarcodes() {

		List<BarcodeVo> allBarcodes = barcodeService.getAllBarcodes();
		return new GateWayResponse<>("fetching all barcode details sucessfully", allBarcodes);

	}

	@PutMapping("/updateBarcode")
	public GateWayResponse<?> updateBarcode(@RequestParam("barcodeId") Long barcodeId, @RequestBody BarcodeVo barcodeVo)
			throws Exception {

		String updateBarcode = barcodeService.updateBarcode(barcodeId, barcodeVo);
		return new GateWayResponse<>("updated barcode successfully", updateBarcode);

	}

	@DeleteMapping("/deleteBarcode")
	public GateWayResponse<?> deleteBarcode(@RequestParam("barcodeId") Long barcodeId) throws Exception {

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
