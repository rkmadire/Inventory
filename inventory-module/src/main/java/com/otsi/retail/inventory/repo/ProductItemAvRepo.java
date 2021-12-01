package com.otsi.retail.inventory.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductItemAv;

@Repository
public interface ProductItemAvRepo extends JpaRepository<ProductItemAv, Long> {

	List<ProductItemAv> findByProductItem(ProductItem save);

	Optional<ProductItemAv> findByProductItemAvId(Long productItemAvId);

}
