package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Repository
public interface BarcodeTextileRepo extends JpaRepository<BarcodeTextile, Long> {

	BarcodeTextile findByBarcode(String barcode);

	BarcodeTextile save(BarcodeTextileVo prodInv);

	List<BarcodeTextile> findByCreationDateBetweenOrderByLastModifiedAsc(LocalDate fromDate, LocalDate toDate);

	Optional<BarcodeTextile> findByBarcodeTextileId(Long barcodeTextileId);

	List<BarcodeTextile> findByCreationDateBetweenAndBarcodeTextileIdOrderByLastModifiedAsc(LocalDate fromDate,
			LocalDate toDate, Long barcodeTextileId);

	List<BarcodeTextile> findByCreationDateBetweenAndBarcodeOrderByLastModifiedAsc(LocalDate fromDate, LocalDate toDate,
			String barcode);

	boolean existsByBarcode(String barcode);

	List<BarcodeTextile> findByProductTextileEmpId(String empId);

	List<BarcodeTextile> findByBarcodeTextileIdIn(List<Long> bars);

	List<BarcodeTextile> findByBarcodeIn(List<String> barcode);

	List<BarcodeTextile> findByProductTextileStoreId(Long storeId);

	boolean existsByProductTextileEmpId(String empId);

}