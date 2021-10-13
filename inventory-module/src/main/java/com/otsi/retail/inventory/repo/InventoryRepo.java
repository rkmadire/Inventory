package com.otsi.retail.inventory.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.ProductItem;

@Repository
public interface InventoryRepo extends JpaRepository<ProductItem, Long> {

	Optional<ProductItem> findByProductItemId(Long productItemId);

	boolean findByBarcodeBarcode(String barcode);


	ProductItem findByNameAndUomAndCostPriceAndListPrice(String name, String uom, float costPrice, float listPrice);

}
