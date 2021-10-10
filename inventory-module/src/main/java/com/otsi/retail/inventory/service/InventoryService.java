package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Service
public interface InventoryService {

	String createInventory(ProductItemVo vo);

	ProductItemVo getProductByProductId(Long productItemId);

	List<ProductItemVo> getAllProducts();

}
