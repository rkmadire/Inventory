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

	List<ProductItem> findByCreationDateBetweenOrderByLastModifiedDateAsc(LocalDate fromDate, LocalDate toDate);

	ProductItem findByStoreId(Long storeId);

	List<ProductItem> findAllByStoreId(Long storeId);

	List<ProductItem> findByStatus(int status);

	List<ProductItem> findByCreationDateBetweenAndBarcodeIdAndStoreIdAndStatusOrderByLastModifiedDateAsc(
			LocalDate fromDate, LocalDate toDate, String barcodeId, Long storeId, int status);

	List<ProductItem> findByCreationDateBetweenAndStoreIdAndStatusOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, Long storeId, int status);

	List<ProductItem> findByCreationDateBetweenAndProductItemIdAndStoreIdAndStatusOrderByLastModifiedDateAsc(
			LocalDate fromDate, LocalDate toDate, Long productItemId, Long storeId, int status);

}
