package com.otsi.retail.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.Store;

@Repository
public interface StoreRepo extends JpaRepository<Store, Long> {

	Optional<Store> findByStoreId(Long storeId);
}
