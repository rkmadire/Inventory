package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.Barcode;

@Repository
public interface BarcodeRepo extends JpaRepository<Barcode, Long> {

	boolean existsByBarcode(String barcode);

	Barcode findByBarcode(String barcode);

	Optional<Barcode> findByBarcodeId(Long barcodeId);

	List<Barcode> findByBarcodeIn(List<String> barcodeList);

	// Optional<Barcode> findByAttr1AndAttr2AndAttr3(String attr1, String attr2,
	// String attr3);

	List<Barcode> findByCreationDateBetweenOrderByLastModifiedAsc(LocalDate fromDate, LocalDate toDate);

	List<Barcode> findByCreationDateBetweenAndBarcodeIdOrderByLastModifiedAsc(LocalDate fromDate, LocalDate toDate,
			Long barcodeId);


}
