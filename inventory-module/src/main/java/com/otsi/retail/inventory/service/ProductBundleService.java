package com.otsi.retail.inventory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.model.ProductBundle;
import com.otsi.retail.inventory.vo.ProductBundleVo;

@Service
public interface ProductBundleService {

	ProductBundleVo addProductBundle(ProductBundleVo productBundleVo);

	Optional<ProductBundle> getProductBundle(Long id);

	//List<ProductBundleVo> getAllProductBundles();

	String updateProductBundle(ProductBundleVo productBundleVo);

	ProductBundleVo deleteProductBundle(Long id);

	//List<ProductBundleVo> getAllProductBundles(SearchFilterVo searchFilterVo);

	List<ProductBundleVo> getAllProductBundles(LocalDateTime fromDate, LocalDateTime toDate,
			String barcode);

	//List<ProductTextileVo> addProductsToBundle(Long id,List<ProductTextileVo> productTextileVo);

}
