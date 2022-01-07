package com.otsi.retail.inventory.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.commons.ProductStatus;
import com.otsi.retail.inventory.model.ProductTextile;

@Repository
public interface ProductTextileRepo extends JpaRepository<ProductTextile, Long> {

	Optional<ProductTextile> findByProductTextileId(Long productTextileId);

	Optional<ProductTextile> findByEmpId(String empId);

	List<ProductTextile> findByItemMrpBetween(float itemMrpLessThan, float itemMrpGreaterThan);

	Optional<ProductTextile> findByStoreId(Long storeId);

	ProductTextile findByStatus(ProductStatus status);

	List<ProductTextile> findAllByStoreId(Long storeId);

	@Query(value = "select uom from product_textile where :enumName = :Pieces group by uom", nativeQuery = true)
	List<String> findAllValueColumns(String enumName, String Pieces);

}
