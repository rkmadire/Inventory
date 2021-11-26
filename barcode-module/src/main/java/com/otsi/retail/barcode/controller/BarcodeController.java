/**
 * 
 */
package com.otsi.retail.barcode.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.barcode.gatewayresponse.GateWayResponse;
import com.otsi.retail.barcode.model.Barcode;
import com.otsi.retail.barcode.service.BarcodeService;
import com.otsi.retail.barcode.vo.BarcodeVo;


/**
 * @author vasavi
 *
 */
@RestController
@RequestMapping("/barcode")
public class BarcodeController {
	
	@Autowired
	private BarcodeService barcodeService;
	
	@PostMapping("/createBarcode")
	public GateWayResponse<?> createBarcode(@RequestBody BarcodeVo vo) {

		BarcodeVo barcodeSave = barcodeService.createBarcode(vo);
		return new GateWayResponse<>("Barcode created successfully", barcodeSave);

	}

	@GetMapping("/getBarcode")
	public GateWayResponse<?> getBarcode(@RequestParam String barcode) {
		Optional<Barcode> fetchBarcode = barcodeService.getBarcode(barcode);
		return new GateWayResponse<>("fetching Barcode details successfully with barcode", fetchBarcode);
	}


}
