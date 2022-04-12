package com.otsi.retail.inventory.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.ProductBundle;

@Repository
public interface ProductBundleRepo extends JpaRepository<ProductBundle, Long> {

	List<ProductBundle> findByCreatedDateAndStatus(LocalDateTime fromDate, Boolean status);

	List<ProductBundle> findByCreatedDateBetweenAndStatusOrderByLastModifiedDate(LocalDateTime fromDate, Boolean status,
			LocalDateTime toDate);

	List<ProductBundle> findByCreatedDateBetweenAndStatusAndProductTextiles_BarcodeOrderByLastModifiedDateAsc(
			LocalDateTime fromDate, Boolean status, String barcode, LocalDateTime toDate);

	List<ProductBundle> findByProductTextiles_BarcodeAndStatus(String barcode, Boolean status);

	List<ProductBundle> findByStatus(Boolean status);

}
