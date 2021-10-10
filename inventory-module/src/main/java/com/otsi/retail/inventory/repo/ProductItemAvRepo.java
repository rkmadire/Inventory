package com.otsi.retail.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.ProductItemAv;

@Repository
public interface ProductItemAvRepo extends JpaRepository<ProductItemAv, Long> {

}
