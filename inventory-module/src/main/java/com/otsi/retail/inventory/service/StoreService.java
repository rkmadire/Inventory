package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.model.Store;
import com.otsi.retail.inventory.vo.StoresVo;

@Service
public interface StoreService {

	StoresVo saveStore(StoresVo vo);

	Optional<Store> getStore(Long storeId);

	List<Store> getAllStores();

}
