package com.otsi.retail.inventory.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Service
public interface ProductItemService {

	String createInventory(ProductItemVo vo);

	ProductItemVo getProductByProductId(Long productItemId);

	List<ProductItemVo> getAllProducts();

}
