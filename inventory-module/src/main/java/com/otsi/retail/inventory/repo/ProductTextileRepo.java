package com.otsi.retail.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.ProductTextile;

@Repository
public interface ProductTextileRepo extends JpaRepository<ProductTextile, Long> {

}
