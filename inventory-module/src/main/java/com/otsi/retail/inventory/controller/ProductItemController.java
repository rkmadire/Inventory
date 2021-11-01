package com.otsi.retail.inventory.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/inventory")
public class ProductItemController {

	private Logger log = LoggerFactory.getLogger(ProductItemController.class);

	@Autowired
	private ProductItemService productItemService;

	@PostMapping("/createProduct")
	public GateWayResponse<?> createProduct(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to createInventory:" + vo);
		String inventorySave = productItemService.createProduct(vo);
		return new GateWayResponse<>("inventory created successfully", inventorySave);
	}

	@GetMapping("/getProductId")
	public GateWayResponse<?> getProductItemId(@RequestParam("productItemId") Long productItemId) {
		log.info("Recieved request to getProductId:" + productItemId);
		ProductItemVo inventory = productItemService.getProductByProductId(productItemId);
		return new GateWayResponse<>("fetching product details successfully with productId", inventory);
	}

	@PutMapping("/updateProduct")
	public GateWayResponse<?> updateProduct(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to updateProduct:" + vo);
		String inventory = productItemService.updateProduct(vo);
		return new GateWayResponse<>("updating product details successfully", inventory);
	}

	@GetMapping("/getProductName")
	public GateWayResponse<?> getProductByName(@RequestParam("name") String name) {
		log.info("Recieved request to getProductByName:" + name);
		ProductItemVo inventoryName = productItemService.getProductByName(name);
		return new GateWayResponse<>("fetching product details successfully with product name", inventoryName);
	}

	@PostMapping("/getAllProducts")
	public GateWayResponse<?> getAllProducts(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to getAllProducts");
		List<ProductItemVo> allProducts = productItemService.getAllProducts(vo);
		return new GateWayResponse<>("fetching all product details sucessfully", allProducts);
	}

	@GetMapping("/getBarcodeId")
	public GateWayResponse<?> getBarcodeId(@RequestParam("barcodeId") int barcodeId) {
		log.info("Recieved request to getBarcodeId:" + barcodeId);
		ProductItemVo inventory = productItemService.getBarcodeId(barcodeId);
		return new GateWayResponse<>("fetching barcode details successfully with barcodeId", inventory);
	}

	@PostMapping("/getAllBarcodes")
	public GateWayResponse<?> getAllBarcodes(@RequestBody ProductItemVo vo) {
		log.info("Recieved request to getAllBarcodes");
		List<ProductItemVo> allBarcodes = productItemService.getAllBarcodes(vo);
		return new GateWayResponse<>("fetching all barcode details sucessfully", allBarcodes);
	}

}
