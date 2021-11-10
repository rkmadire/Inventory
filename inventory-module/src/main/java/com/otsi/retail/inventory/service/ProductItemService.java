package com.otsi.retail.inventory.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Service
public interface ProductItemService {

	String createBarcode(ProductItemVo vo);

	ProductItemVo getProductByProductId(Long productItemId);

	ProductItemVo getProductByName(String name);

	List<ProductItemVo> getAllProducts(ProductItemVo vo);

	List<ProductItemVo> getAllBarcodes(ProductItemVo vo);

	ProductItemVo getBarcodeId(int barcodeId);

	String addInventory(ProductItemVo vo);

	String updateBarcode(ProductItemVo vo);

	String deleteBarcode(int barcodeId);

}
