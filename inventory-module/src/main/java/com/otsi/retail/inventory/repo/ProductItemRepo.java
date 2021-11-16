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

	List<ProductItem> findByCreationDateBetweenOrderByLastModifiedDateAsc(LocalDate fromDate, LocalDate toDate);

	List<ProductItem> findByCreationDateBetweenAndProductItemIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, Long productItemId);

	List<ProductItem> findByCreationDateBetweenAndBarcodeIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, Long barcodeId);

	Optional<ProductItem> findByBarcodeId(long barcodeId);

	boolean existsByName(String name);

	boolean existsByBarcodeId(Long barcodeId);


}
