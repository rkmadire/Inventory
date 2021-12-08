package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.Adjustments;

@Repository
public interface AdjustmentRepo extends JpaRepository<Adjustments, Long> {

	Optional<Adjustments> findByAdjustmentId(Long adjustmentId);


	List<Adjustments> findByCreationDateBetweenOrderByLastModifiedDateAsc(LocalDate fromDate, LocalDate toDate);


	List<Adjustments> findByCreationDateBetweenAndAdjustmentIdOrderByLastModifiedDateAsc(LocalDate fromDate,
			LocalDate toDate, Long adjustmentId);

}
