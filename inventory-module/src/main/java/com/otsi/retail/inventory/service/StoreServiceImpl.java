package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.StoreMapper;
import com.otsi.retail.inventory.model.Store;
import com.otsi.retail.inventory.repo.StoreRepo;
import com.otsi.retail.inventory.vo.StoresVo;

@Component
public class StoreServiceImpl implements StoreService {

	private Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

	@Autowired
	private StoreMapper storeMapper;

	@Autowired
	private StoreRepo storeRepo;

	@Override
	public StoresVo saveStore(StoresVo vo) {
		log.debug("debugging saveStore:" + vo);
		Store s = storeMapper.VoToEntity(vo);
		Store storeSave = storeRepo.save(s);
		StoresVo store = storeMapper.EntityToVo(storeSave);
		log.warn("we are testing if storeis saved...");
		log.info("after saving store:" + store.toString());
		return store;
	}

	@Override
	public Optional<Store> getStore(Long storeId) {
		log.debug("debugging getStore");
		Optional<Store> vo = storeRepo.findByStoreId(storeId);
		if (!(vo.isPresent())) {
			log.error("store  record is not found");
			throw new RecordNotFoundException("store record is not found");
		}
		log.warn("we are testing is fetching store by storeId");
		log.info("after fetching store:" + vo.toString());
		return vo;

	}

	@Override
	public List<Store> getAllStores() {
		log.debug("debugging getAllDebitNotes");
		List<Store> vo = storeRepo.findAll();
		log.warn("we are testing is fetching all stores");
		log.info("after fetching all stores:" + vo.toString());
		return vo;
	}

	@Override
	public String updateStore(StoresVo storesVo) {
		log.debug("debugging updateStore:" + storesVo);
		Optional<Store> store = storeRepo.findById(storesVo.getStoreId());
		if (!(store.isPresent())) {
			throw new RecordNotFoundException("store is  not found with id: " + storesVo.getStoreId());
		}
		Store str = storeMapper.VoToEntity(storesVo);
		str.setStoreId(storesVo.getStoreId());
		str.setLastmodified(storesVo.getLastmodified());
		Store storeUpdate = storeRepo.save(str);
		log.warn("we are checking if store is updating...");
		log.info("after updating store details:" + storeUpdate.toString());
		return "store updated successfully";
	}

	@Override
	public String deleteStore(Long storeId) {
		log.debug("debugging deleteStore:" + storeId);
		Optional<Store> store = storeRepo.findById(storeId);
		if (!(store.isPresent())) {
			throw new RecordNotFoundException("domain not found with id: " + storeId);
		}
		storeRepo.delete(store.get());
		log.warn("we are checking if store is fetching...");
		log.info("after fetching store details:" + storeId);
		return "store deleted succesfully: " + storeId;
	}

}
