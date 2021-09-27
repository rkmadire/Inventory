package com.otsi.retail.inventory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.InventoryMapper;
import com.otsi.retail.inventory.model.Inventory;
import com.otsi.retail.inventory.repo.InventoryRepo;
import com.otsi.retail.inventory.vo.InventoryVo;

@Component
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepo inventoryRepo;

	@Autowired
	private InventoryMapper inventoryMapper;

	@Override
	public InventoryVo createInventory(InventoryVo vo) {

		Inventory inv = inventoryMapper.VoToEntity(vo);

		InventoryVo inventory = inventoryMapper.EntityToVo(inventoryRepo.save(inv));
		return inventory;
	}

	@Override
	public Optional<Inventory> getInventoryByInventoryId(int inventoryId) {

		Optional<Inventory> vo = inventoryRepo.findByInventoryId(inventoryId);
		if (!(vo.isPresent())) {
			throw new RecordNotFoundException("inventory record is not found");
		}

		return vo;

	}

}
