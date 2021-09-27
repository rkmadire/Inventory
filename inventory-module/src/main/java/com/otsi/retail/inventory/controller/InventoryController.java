package com.otsi.retail.inventory.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.model.Inventory;
import com.otsi.retail.inventory.service.InventoryService;
import com.otsi.retail.inventory.vo.InventoryVo;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	

	@PostMapping("/createInventory")
	public GateWayResponse<?> createInventory(@RequestBody InventoryVo vo) {
		InventoryVo inventorySave = inventoryService.createInventory(vo);
		return new GateWayResponse<>("inventory created successfully", inventorySave);

	}

	@GetMapping("/getInventoryId")
	public GateWayResponse<?> getInventoryId(@RequestParam int inventoryId) {
		Optional<Inventory> inventory = inventoryService.getInventoryByInventoryId(inventoryId);
		return new GateWayResponse<>("fetching inventory details successfully with inventoryId", inventory);
	}

}
