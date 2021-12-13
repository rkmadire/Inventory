package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.vo.ProductItemVo;

@Service
public interface ProductItemService {

	String createBarcode(ProductItemVo vo);

	ProductItemVo getProductByProductId(Long productItemId);

	ProductItemVo getProductByName(String name);

	List<ProductItemVo> getAllProducts(ProductItemVo vo);

	List<ProductItemVo> getAllBarcodes(ProductItemVo vo);

	ProductItemVo getBarcodeId(String barcodeId);

	String updateInventory(ProductItemVo vo);

	String updateBarcode(ProductItemVo vo);

	String deleteBarcode(String barcodeId);

	String fromNewSale(Map<String,Integer> map);

}