package com.otsi.retail.inventory.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.service.InventoryService;
import com.otsi.retail.inventory.vo.ProductItemVo;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@PostMapping("/createProduct")
	public GateWayResponse<?> createInventory(@RequestBody ProductItemVo vo) {
		String inventorySave = inventoryService.createInventory(vo);
		return new GateWayResponse<>("inventory created successfully", inventorySave);

	}

	@GetMapping("/getProductId")
	public GateWayResponse<?> getProductId(@RequestParam Long productItemId) {
		ProductItemVo inventory = inventoryService.getProductByProductId(productItemId);
		return new GateWayResponse<>("fetching inventory details successfully with inventoryId", inventory);
	}
	
	@GetMapping("/getAllProducts")
	public GateWayResponse<?> getAllProducts() {

		List<ProductItemVo> allProducts = inventoryService.getAllProducts();
		return new GateWayResponse<>("fetching all barcode details sucessfully", allProducts);

	}

}
