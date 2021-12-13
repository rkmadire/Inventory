package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.model.ProductTransaction;

@Repository
public interface ProductTransactionRepo extends JpaRepository<ProductTransaction, Long> {

	ProductTransaction findByBarcodeId(Long barcodeTextileId);

	List<ProductTransaction> findByCreationDateBetweenAndBarcodeIdAndMasterFlagOrderByLastModifiedAsc(
			LocalDate fromDate, LocalDate toDate, Long barcodeTextileId, boolean masterFlag);

	List<ProductTransaction> findByCreationDateBetweenAndMasterFlagOrderByLastModifiedAsc(LocalDate fromDate,
			LocalDate toDate, boolean masterFlag);

	ProductTransaction findByQuantity(int qty);

}
