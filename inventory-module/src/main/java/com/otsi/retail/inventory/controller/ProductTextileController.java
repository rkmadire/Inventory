package com.otsi.retail.inventory.controller;

import java.util.Optional;
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
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.service.ProductTextileService;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;

@RestController
@RequestMapping("/productTextile")
public class ProductTextileController {

	private Logger log = LoggerFactory.getLogger(ProductTextileController.class);

	@Autowired
	private ProductTextileService productTextileService;

	@PostMapping("/saveProductTextile")
	public GateWayResponse<?> saveProductTextile(@RequestBody ProductTextileVo textileVo) {
		log.info("Recieved request to saveTextile:" + textileVo);
		String textileSave = productTextileService.saveProductTextile(textileVo);
		return new GateWayResponse<>("product textile saved successfully", textileSave);

	}

	@GetMapping("/getProductTextile")
	public GateWayResponse<?> getProductTextileById(@RequestParam("productTextileId") Long productTextileId) {
		log.info("Recieved request to getProductTextile:" + productTextileId);
		ProductTextileVo textile = productTextileService.getProductTextile(productTextileId);
		return new GateWayResponse<>("fetching product textile details successfully with id", textile);
	}
}
