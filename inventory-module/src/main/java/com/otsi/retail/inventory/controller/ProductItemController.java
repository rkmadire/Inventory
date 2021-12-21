package com.otsi.retail.inventory.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.otsi.retail.inventory.service.ProductItemService;
import com.otsi.retail.inventory.vo.ProductItemVo;

/**
 * @author vasavi
 *
 */
@RestController
@RequestMapping("/inventoryRetail")
public class ProductItemController {

	private Logger log = LogManager.getLogger(ProductItemController.class);

	@Autowired
	private ProductItemService productItemService;

	@PostMapping("/createBarcode")
	public GateWayResponse<?> createBarcode(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to createBarcode:" + vo);
		String inventorySave = productItemService.createBarcode(vo);
		return new GateWayResponse<>("Barcode created successfully", inventorySave);
	}

	@GetMapping("/getProductId")
	public GateWayResponse<?> getProductItemId(@RequestParam("productItemId") Long productItemId,
			@RequestParam("storeId") Long storeId) {
		log.info("Recieved request to getProductId:" + productItemId);
		ProductItemVo inventory = productItemService.getProductByProductId(productItemId, storeId);
		return new GateWayResponse<>("fetching product details successfully with productId", inventory);
	}

	@PutMapping(value = "/updateBarcode")
	public GateWayResponse<?> updateBarcode(@RequestBody ProductItemVo vo) {
		log.info("Received Request to updateBarcode :" + vo.toString());
		String updateBarcode = productItemService.updateBarcode(vo);
		return new GateWayResponse<>("Barcode updated successfully", updateBarcode);
	}

	@DeleteMapping("/deleteBarcode")
	public GateWayResponse<?> deleteBarcode(@RequestParam("barcodeId") String barcodeId) {
		log.info("Received Request to deleteBarcode :" + barcodeId);
		String deleteBarcode = productItemService.deleteBarcode(barcodeId);
		return new GateWayResponse<>("Barcode deleted successfully", deleteBarcode);

	}

	@PutMapping("/updateInventory")
	public GateWayResponse<?> updateInventory(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to updateProduct:" + vo);
		String inventory = productItemService.updateInventory(vo);
		return new GateWayResponse<>("updating inventory details successfully", inventory);
	}

	@GetMapping("/getProductName")
	public GateWayResponse<?> getProductByName(@RequestParam("name") String name,@RequestParam("storeId") Long storeId) {
		log.info("Recieved request to getProductByName:" + name+"and storeId is:"+storeId);
		ProductItemVo inventoryName = productItemService.getProductByName(name,storeId);
		return new GateWayResponse<>("fetching product details successfully with product name", inventoryName);
	}

	@PostMapping("/getAllProducts")
	public GateWayResponse<?> getAllProducts(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to getAllProducts");
		List<ProductItemVo> allProducts = productItemService.getAllProducts(vo);
		return new GateWayResponse<>("fetching all product details sucessfully", allProducts);
	}

	@GetMapping("/getBarcodeId")
	public GateWayResponse<?> getBarcodeId(@RequestParam("barcodeId") String barcodeId,
			@RequestParam("storeId") Long storeId) {
		log.info("Recieved request to getBarcodeId:" + barcodeId);
		ProductItemVo inventory = productItemService.getBarcodeId(barcodeId, storeId);
		return new GateWayResponse<>("fetching barcode details successfully with barcodeId", inventory);
	}

	@PostMapping("/getAllBarcodes")
	public GateWayResponse<?> getAllBarcodes(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to getAllBarcodes");
		List<ProductItemVo> allBarcodes = productItemService.getAllBarcodes(vo);
		return new GateWayResponse<>("fetching all barcode details sucessfully", allBarcodes);
	}

	// @RabbitListener(queues = MQConfig.INVENTORYQUEUE)
	public GateWayResponse<?> fromNewsaleForRetail(@RequestBody Map<String, Integer> map) {
		String allNewsale = productItemService.fromNewSaleForRetail(map);

		return new GateWayResponse<>("fetching all barcode details sucessfully", allNewsale);
	}

	@PostMapping("/saveProductList")
	public GateWayResponse<?> saveProductList(@RequestBody List<ProductItemVo> productItemVos) {
		log.info("Received Request to saveProductList:" + productItemVos);
		String saveVoList = productItemService.saveProductList(productItemVos);
		return new GateWayResponse<>("saving list of product", saveVoList);

	}
}
