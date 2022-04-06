package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.Adjustments;

@Repository
public interface AdjustmentRepo extends JpaRepository<Adjustments, Long> {

	Adjustments findByCurrentBarcodeId(String currentBarcodeId);

	List<Adjustments> findAllByAdjustmentId(Long adjustmentId);

	
	List<Adjustments> findByCreationDateBetweenAndCurrentBarcodeIdAndCommentsOrderByLastModifiedDateAsc(
			LocalDate fromDate, LocalDate toDate, String currentBarcodeId, String string);

	List<Adjustments> findByAdjustmentIdInAndComments(List<Long> effectingId, String string);

	List<Adjustments> findByCurrentBarcodeIdAndAdjustmentIdInAndComments(String currentBarcodeId,
			List<Long> effectingId, String string);

	List<Adjustments> findByCreationDateBetweenAndCommentsOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, String string);

	List<Adjustments> findByCreationDateAndComments(LocalDate fromDate, String string);

	
}
