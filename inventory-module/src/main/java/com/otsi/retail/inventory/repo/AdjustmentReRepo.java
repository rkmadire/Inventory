package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.AdjustmentsRe;

@Repository
public interface AdjustmentReRepo extends JpaRepository<AdjustmentsRe, Long> {

	AdjustmentsRe findByCurrentBarcodeId(String currentBarcodeId);

	List<AdjustmentsRe> findByCreationDateBetweenAndCurrentBarcodeIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, String currentBarcodeId);

	List<AdjustmentsRe> findByCreationDateBetweenOrderByLastModifiedDateAsc(LocalDate fromDate, LocalDate toDate);

	List<AdjustmentsRe> findByAdjustmentReIdIn(List<Long> effectingId);

	List<AdjustmentsRe> findByCurrentBarcodeIdAndAdjustmentReIdIn(String currentBarcodeId, List<Long> effectingId);

}
