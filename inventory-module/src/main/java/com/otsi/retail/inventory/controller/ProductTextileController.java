package com.otsi.retail.inventory.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.rabbitmq.MQConfig;
import com.otsi.retail.inventory.service.ProductTextileService;
import com.otsi.retail.inventory.vo.AdjustmentsVo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.ProductTransactionVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;
import com.otsi.retail.inventory.vo.UpdateInventoryRequest;

/**
 * @author vasavi
 *
 */
@RestController
@RequestMapping("/inventoryTextile")
public class ProductTextileController {

	private Logger log = LoggerFactory.getLogger(ProductTextileController.class);

	@Autowired
	private ProductTextileService productTextileService;

	@PostMapping("/addBarcode_Textile")
	public GateWayResponse<?> addBarcodeTextile(@RequestBody BarcodeTextileVo textileVo) {
		log.info("Recieved request to saveTextile:" + textileVo);
		String textileSave = productTextileService.addBarcodeTextile(textileVo);
		return new GateWayResponse<>("barcode textile saved successfully", textileSave);

	}

	@GetMapping("/getProductTextile")
	public GateWayResponse<?> getProductTextileById(@RequestParam("productTextileId") Long productTextileId) {
		log.info("Recieved request to getProductTextile:" + productTextileId);
		ProductTextileVo textile = productTextileService.getProductTextile(productTextileId);
		return new GateWayResponse<>("fetching product textile details successfully with id", textile);
	}

	@PutMapping(value = "/updateBarcode_Textile")
	public GateWayResponse<?> updateBarcodeTextile(@RequestBody BarcodeTextileVo vo) {
		log.info("Received Request to updateBarcodeTextile :" + vo.toString());
		String updateBarcode = productTextileService.updateBarcodeTextile(vo);
		return new GateWayResponse<>("Barcode textile updated successfully", updateBarcode);
	}

	@DeleteMapping("/deleteBarcode_Textile")
	public GateWayResponse<?> deleteBarcodeTextile(@RequestParam("barcodeTextileId") Long barcodeTextileId) {
		log.info("Received Request to deleteBarcodeTextile:" + barcodeTextileId);
		String deleteBarcode = productTextileService.deleteBarcodeTextile(barcodeTextileId);
		return new GateWayResponse<>("Barcode textile deleted successfully", deleteBarcode);

	}

	@GetMapping("/getBarcodeTextile")
	public GateWayResponse<?> getBarcodeTextile(@RequestParam("barcode") String barcode) {
		log.info("Recieved request to getProductTextile:" + barcode);
		BarcodeTextileVo textile = productTextileService.getBarcodeTextile(barcode);
		return new GateWayResponse<>("fetching barcode textile details successfully with barcode", textile);
	}

	@PostMapping("/getAllBarcodeTextiles")
	public GateWayResponse<?> getAllBarcodes(@RequestBody SearchFilterVo vo) {
		log.info("Recieved request to getAllBarcodes");
		List<BarcodeTextileVo> allBarcodes = productTextileService.getAllBarcodes(vo);
		return new GateWayResponse<>("fetching all barcode textile details sucessfully", allBarcodes);
	}
	
	//@RabbitListener(queues = MQConfig.inventory_queue_textile)
	@PostMapping("/inventoryUpdateForTextile")
	public GateWayResponse<?> inventoryUpdateForTextile(@RequestBody List<UpdateInventoryRequest> request) {
		String barcodeDetails = productTextileService.inventoryUpdateForTextile(request);
		return new GateWayResponse<>("update inventory from newsale", barcodeDetails);

	}
	
	@PostMapping("/getAllAdjustments")
	public GateWayResponse<?> getAllAdjustments(@RequestBody AdjustmentsVo vo) {
		log.info("Recieved request to getAllBarcodesForTextile");
		List<AdjustmentsVo> allBarcodes = productTextileService.getAllAdjustments(vo);
		return new GateWayResponse<>("fetching all adjusment details sucessfully", allBarcodes);
	}
}
