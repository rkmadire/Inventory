package com.otsi.retail.inventory.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.model.Inventory;
import com.otsi.retail.inventory.vo.InventoryVo;

@Service
public interface InventoryService {

	InventoryVo createInventory(InventoryVo vo);

	Optional<Inventory> getInventoryByInventoryId(int inventoryId);

}
