package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.ProductItem;

@Repository
public interface ProductItemRepo extends JpaRepository<ProductItem, Long> {

	Optional<ProductItem> findByProductItemId(Long productItemId);

	ProductItem findByNameAndUomAndCostPriceAndListPrice(String name, String uom, float costPrice, float listPrice);

	Optional<ProductItem> findByName(String name);

	ProductItem findByBarcodeId(String barcodeId);

	boolean existsByName(String name);

	boolean existsByBarcodeId(String barcodeId);

	ProductItem findByStoreId(Long storeId);

	List<ProductItem> findAllByStoreId(Long storeId);

	List<ProductItem> findByCreationDateBetweenAndProductItemIdAndStoreIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, Long productItemId, Long storeId);

	List<ProductItem> findByCreationDateBetweenAndStoreIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, Long storeId);

	List<ProductItem> findByCreationDateBetweenAndBarcodeIdAndStoreIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, String barcodeId, Long storeId);

}
