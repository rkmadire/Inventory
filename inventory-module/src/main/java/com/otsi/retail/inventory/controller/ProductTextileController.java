package com.otsi.retail.inventory.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.otsi.retail.inventory.vo.InventoryUpdateVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;

/**
 * @author vasavi
 *
 */
@RestController
@RequestMapping("/inventoryTextile")
public class ProductTextileController {

	private Logger log = LogManager.getLogger(ProductTextileController.class);

	@Autowired
	private ProductTextileService productTextileService;

	@PostMapping("/addBarcode_Textile")
	public GateWayResponse<?> addBarcodeTextile(@RequestBody ProductTextileVo textileVo) {
		log.info("Recieved request to saveTextile:" + textileVo);
		String textileSave = productTextileService.addBarcodeTextile(textileVo);
		return new GateWayResponse<>("barcode textile saved successfully", textileSave);

	}

	@PutMapping(value = "/updateBarcode_Textile")
	public GateWayResponse<?> updateBarcodeTextile(@RequestBody ProductTextileVo vo) {
		log.info("Received Request to updateBarcodeTextile :" + vo.toString());
		String updateBarcode = productTextileService.updateBarcodeTextile(vo);
		return new GateWayResponse<>("Barcode textile updated successfully", updateBarcode);
	}

	@DeleteMapping("/deleteBarcode_Textile")
	public GateWayResponse<?> deleteBarcodeTextile(@RequestParam("barcode") String barcode) {
		log.info("Received Request to deleteBarcodeTextile:" + barcode);
		String deleteBarcode = productTextileService.deleteBarcodeTextile(barcode);
		return new GateWayResponse<>("Barcode textile deleted successfully", deleteBarcode);

	}

	@GetMapping("/getBarcodeTextile")
	public GateWayResponse<?> getBarcodeTextile(@RequestParam("barcode") String barcode,
			@RequestParam("storeId") Long storeId) {
		log.info("Recieved request to getProductTextile:" + barcode);
		ProductTextileVo textile = productTextileService.getBarcodeTextile(barcode, storeId);
		return new GateWayResponse<>("fetching barcode textile details successfully with barcode", textile);
	}

	@PostMapping("/getAllBarcodeTextiles")
	public GateWayResponse<?> getAllBarcodes(@RequestBody SearchFilterVo vo) {
		log.info("Recieved request to getAllBarcodes");
		List<ProductTextileVo> allBarcodes = productTextileService.getAllBarcodes(vo);
		return new GateWayResponse<>("fetching all barcode textile details sucessfully", allBarcodes);
	}

	@RabbitListener(queues = MQConfig.inventory_queue)
	public void inventoryUpdate(@RequestBody List<InventoryUpdateVo> request) {
		productTextileService.inventoryUpdate(request);
	}

	@PostMapping("/getAllAdjustments")
	public GateWayResponse<?> getAllAdjustments(@RequestBody AdjustmentsVo vo) {
		log.info("Recieved request to getAllAdjustments:" + vo);
		List<AdjustmentsVo> allBarcodes = productTextileService.getAllAdjustments(vo);
		return new GateWayResponse<>("fetching all adjusment details sucessfully", allBarcodes);
	}

	@PostMapping("/saveProductTextileList")
	public GateWayResponse<?> saveProductTextileList(@RequestBody List<ProductTextileVo> barcodeTextileVos,
			Long storeId) {
		log.info("Received Request to saveProductTextileList:" + barcodeTextileVos);
		String saveVoList = productTextileService.saveProductTextileList(barcodeTextileVos, storeId);
		return new GateWayResponse<>("saving list of product textile", saveVoList);

	}

	@GetMapping("/getValuesFromProductTextileColumns")
	public GateWayResponse<?> getValuesFromProductTextileColumns(@RequestParam("enumName") String enumName) {
		log.info("Recieved request to getValuesFromProductTextileColumns:" + enumName);
		List<String> enumVo = productTextileService.getValuesFromProductTextileColumns(enumName);
		return new GateWayResponse<>("fetching all " + enumName + " textile details sucessfully", enumVo);
	}

	@GetMapping("/getAllColumns")
	public GateWayResponse<?> getAllColumns(@RequestParam Long domainId) {
		log.info("Received Request to getAllColumns...");
		List<String> columns = productTextileService.getAllColumns(domainId);
		return new GateWayResponse<>("fetching all Column details", columns);

	}

	@PostMapping("/getBarcodeTextileReports")
	public GateWayResponse<?> getBarcodeTextileReports(@RequestBody SearchFilterVo vo) {
		log.info("Recieved request to getBarcodeTextileReports:" + vo);
		List<ProductTextileVo> allBarcodes = productTextileService.getBarcodeTextileReports(vo);
		return new GateWayResponse<>("fetching all barcode textile details sucessfully", allBarcodes);
	}

	@PostMapping("/getBarcodes")
	public GateWayResponse<?> getBarcodes(@RequestBody List<String> barcode) {
		log.info("Received Request to getBarcodeDetails:" + barcode);
		List<ProductTextileVo> barcodeDetails = productTextileService.getBarcodes(barcode);
		return new GateWayResponse<>("fetching all barcode details", barcodeDetails);

	}
	
	@GetMapping("/getTextileParentBarcode")
	public GateWayResponse<?> getTextileParentBarcode(@RequestParam("parentBarcode") String parentBarcode) {
		log.info("Recieved request to getTextileParentBarcode:" + parentBarcode);
		ProductTextileVo textile = productTextileService.getTextileParentBarcode(parentBarcode);
		return new GateWayResponse<>("fetching parent barcode textile details successfully", textile);
	}
}
