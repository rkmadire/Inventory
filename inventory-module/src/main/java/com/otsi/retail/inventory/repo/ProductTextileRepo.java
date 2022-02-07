package com.otsi.retail.inventory.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.commons.ProductStatus;
import com.otsi.retail.inventory.model.ProductTextile;

@Repository
public interface ProductTextileRepo extends JpaRepository<ProductTextile, Long> {

	Optional<ProductTextile> findByProductTextileId(Long productTextileId);

	List<ProductTextile> findByEmpId(String empId);

	List<ProductTextile> findByItemMrpBetweenAndStoreId(float itemMrpLessThan, float itemMrpGreaterThan, Long storeId);

	Optional<ProductTextile> findByStoreId(Long storeId);

	ProductTextile findByStatus(ProductStatus status);

	List<ProductTextile> findAllByStoreId(Long storeId);

	

}
