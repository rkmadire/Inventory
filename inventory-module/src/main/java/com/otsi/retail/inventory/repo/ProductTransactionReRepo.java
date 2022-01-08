package com.otsi.retail.inventory.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.ProductTransactionRe;

@Repository
public interface ProductTransactionReRepo extends JpaRepository<ProductTransactionRe, Long> {

}
