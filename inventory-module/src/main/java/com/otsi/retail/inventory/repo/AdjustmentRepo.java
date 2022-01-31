package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.Adjustments;

@Repository
public interface AdjustmentRepo extends JpaRepository<Adjustments, Long> {

	Adjustments findByCurrentBarcodeId(String currentBarcodeId);

	List<Adjustments> findByCreationDateBetweenAndCurrentBarcodeIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, String currentBarcodeId);

	List<Adjustments> findByCreationDateBetweenOrderByLastModifiedDateAsc(LocalDate fromDate, LocalDate toDate);

	List<Adjustments> findAllByAdjustmentId(Long adjustmentId);

	List<Adjustments> findByCurrentBarcodeIdAndAdjustmentIdIn(String currentBarcodeId, List<Long> effectingId);

	List<Adjustments> findByAdjustmentIdIn(List<Long> effectingId);

}
