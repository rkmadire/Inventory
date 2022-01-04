package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.commons.ProductStatus;
import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;

@Repository
public interface BarcodeTextileRepo extends JpaRepository<BarcodeTextile, Long> {

	BarcodeTextile findByBarcode(String barcode);

	BarcodeTextile save(BarcodeTextileVo prodInv);

	Optional<BarcodeTextile> findByBarcodeTextileId(Long barcodeTextileId);

	List<BarcodeTextile> findByCreationDateBetweenAndBarcodeTextileIdOrderByLastModifiedAsc(LocalDate fromDate,
			LocalDate toDate, Long barcodeTextileId);

	List<BarcodeTextile> findByCreationDateBetweenAndBarcodeOrderByLastModifiedAsc(LocalDate fromDate, LocalDate toDate,
			String barcode);

	boolean existsByBarcode(String barcode);

	List<BarcodeTextile> findByProductTextileEmpId(String empId);

	List<BarcodeTextile> findByBarcodeTextileIdIn(List<Long> bars);

	List<BarcodeTextile> findByProductTextileStoreId(Long storeId);

	boolean existsByProductTextileEmpId(String empId);

	List<BarcodeTextile> findByProductTextileStatus(ProductStatus status);

	List<BarcodeTextile> findByCreationDateBetweenAndProductTextileStatusOrderByLastModifiedAsc(LocalDate fromDate,
			LocalDate toDate, ProductStatus status);

	List<BarcodeTextile> findAllByProductTextileStoreId(Long storeId);

	List<BarcodeTextile> findByCreationDateBetweenAndProductTextileStatusAndProductTextileStoreIdOrderByLastModifiedAsc(
			LocalDate fromDate, LocalDate toDate, ProductStatus status, Long storeId);

	List<BarcodeTextile> findByCreationDateBetweenAndBarcodeAndProductTextileStoreIdOrderByLastModifiedAsc(
			LocalDate fromDate, LocalDate toDate, String barcode, Long storeId);

	List<BarcodeTextile> findByCreationDateBetweenAndBarcodeTextileIdAndProductTextileStoreIdOrderByLastModifiedAsc(
			LocalDate fromDate, LocalDate toDate, Long barcodeTextileId, Long storeId);

	List<BarcodeTextile> findByProductTextileStoreIdAndProductTextileStatus(Long storeId, ProductStatus status);

	BarcodeTextile findByProductTextileParentBarcode(String barcode);

}
