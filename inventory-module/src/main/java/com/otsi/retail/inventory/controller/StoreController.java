package com.otsi.retail.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.otsi.retail.inventory.model.Store;
import com.otsi.retail.inventory.service.StoreService;
import com.otsi.retail.inventory.vo.StoresVo;

@RestController
@RequestMapping("/store")
public class StoreController {

	private Logger log = LoggerFactory.getLogger(StoreController.class);

	@Autowired
	private StoreService storeService;

	@PostMapping("/saveStore")
	public GateWayResponse<?> saveStore(@RequestBody StoresVo vo) {
		log.info("Received Request to saveStore:" + vo);
		StoresVo storeSave = storeService.saveStore(vo);
		return new GateWayResponse<>("store saved successfully", storeSave);

	}

	@GetMapping("/getStoreId")
	public GateWayResponse<?> getStoreById(@RequestParam Long storeId) {
		log.info("Received Request to getStoreById:" + storeId);
		Optional<Store> inventory = storeService.getStore(storeId);
		return new GateWayResponse<>("fetching store details successfully with storeId", inventory);
	}

	@GetMapping("/getAllStores")
	public GateWayResponse<?> getAllStores() {
		log.info("Received Request to getAllStores");
		List<Store> allStores = storeService.getAllStores();
		return new GateWayResponse<>("fetching all stores  sucessfully", allStores);

	}

	@PutMapping("/updateStore")
	public GateWayResponse<?> updateStore(@RequestBody StoresVo storesVo) throws Exception {
		log.info("Recieved request to updateStore:" + storesVo);
		String updateStore = storeService.updateStore(storesVo);
		return new GateWayResponse<>("updated store successfully", updateStore);

	}

	@DeleteMapping("/deleteStore")
	public GateWayResponse<?> deleteStore(@RequestParam("storeId") Long storeId) throws Exception {
		log.info("Recieved request to deleteStore:" + storeId);
		String deleteStore = storeService.deleteStore(storeId);
		return new GateWayResponse<>("store deleted successfully", deleteStore);

	}

}
