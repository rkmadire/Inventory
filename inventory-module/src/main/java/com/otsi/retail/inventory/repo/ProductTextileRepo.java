package com.otsi.retail.inventory.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductTextile;

@Repository
public interface ProductTextileRepo extends JpaRepository<ProductTextile, Long> {

	Optional<ProductTextile> findByProductTextileId(Long productTextileId);

	Optional<ProductTextile> findByEmpId(String empId);

	List<ProductTextile> findByItemMrpBetween(float itemMrpLessThan, float itemMrpGreaterThan);


}
