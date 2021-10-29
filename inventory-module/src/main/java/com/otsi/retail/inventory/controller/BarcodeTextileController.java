package com.otsi.retail.inventory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.service.BarcodeTextileService;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;

@RestController
@RequestMapping("/barcodeTextile")
public class BarcodeTextileController {
	
	private Logger log = LoggerFactory.getLogger(BarcodeTextileController.class);

	@Autowired
	private BarcodeTextileService barcodeTextileService;
	
	@PostMapping("/saveBarcodeTextile")
	public GateWayResponse<?> saveBarcodeTextile(@RequestBody BarcodeTextileVo barcodeTextileVo) {
		log.info("Recieved request to saveBarcodeTextile:" + barcodeTextileVo);
		String textileSave = barcodeTextileService.saveBarcodeTextile(barcodeTextileVo);
		return new GateWayResponse<>("barcode textile saved successfully", textileSave);

	}
	
	@GetMapping("/getBarcodeTextile")
	public GateWayResponse<?> getBarcodeTextile(@RequestParam("barcode") String barcode) {
		log.info("Recieved request to getProductTextile:" + barcode);
		BarcodeTextileVo textile = barcodeTextileService.getBarcodeTextile(barcode);
		return new GateWayResponse<>("fetching barcode textile details successfully with id", textile);
	}

}
