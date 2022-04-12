package com.otsi.retail.inventory.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.ProductEnum;
import com.otsi.retail.inventory.commons.ProductStatus;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.ProductBundleMapper;
import com.otsi.retail.inventory.mapper.ProductTextileMapper;
import com.otsi.retail.inventory.model.ProductBundle;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.repo.ProductBundleRepo;
import com.otsi.retail.inventory.repo.ProductTextileRepo;
import com.otsi.retail.inventory.vo.ProductBundleVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;

@Component
public class ProductBundleServiceImpl implements ProductBundleService {

	private Logger log = LogManager.getLogger(ProductBundleServiceImpl.class);

	@Autowired
	private ProductBundleMapper productBundleMapper;

	@Autowired
	private ProductBundleRepo productBundleRepo;

	@Autowired
	private ProductTextileRepo productTextileRepo;
    
	@Autowired
	private ProductTextileMapper productTextileMapper;

	@Transactional
	@Override
	public ProductBundleVo addProductBundle(ProductBundleVo productBundleVo) {
		ProductBundle bundle = productBundleMapper.VoToEntity(productBundleVo);
		List<ProductTextileVo> textiles = productBundleVo.getProductTextiles();
		ProductStatus status = ProductStatus.ENABLE;
		textiles.stream().forEach(productTextile -> {
			ProductTextile textileBarcode = productTextileRepo.findByBarcodeAndStatus(productTextile.getBarcode(),
					status);
			if (textileBarcode != null) {
				productTextile.setSellingTypeCode(ProductEnum.PRODUCTBUNDLE);
			}
		});
		bundle.setProductTextiles(productTextileMapper.VoToEntity(textiles));
		productBundleVo = productBundleMapper.EntityToVo(productBundleRepo.save(bundle));
		return productBundleVo;
	}

	
	/*@Override
	 * public List<ProductTextileVo> addProductsToBundle(Long id,
	 * List<ProductTextileVo> productTextileVos) { Optional<ProductBundle> bundle =
	 * productBundleRepo.findById(id); if (bundle.isPresent()) {
	 * List<ProductTextile> productTextiles = productTextileRepo.findAll();
	 * productTextiles.stream().forEach(productTextile -> { if
	 * (productTextile.getSellingTypeCode().equals(ProductEnum.PRODUCT_BUNDLE)) {
	 * bundle.get().setProductTextiles(productTextiles);
	 * 
	 * } else {
	 * 
	 * } }); productTextileVos = productTextileMapper.EntityToVo(productTextiles); }
	 * return productTextileVos; }
	 */

	@Override
	public Optional<ProductBundle> getProductBundle(Long id) {
		log.debug("debugging getProductBundle:" + id);
		Optional<ProductBundle> productBundle = productBundleRepo.findById(id);
		if (!(productBundle.isPresent())) {
			log.error("bundle  record is not found");
			throw new RecordNotFoundException("bundle record is not found");
		}
		log.info("after fetching product bundle details:" + productBundle.toString());
		return productBundle;
	}

	@Override
	public List<ProductBundleVo> getAllProductBundles(LocalDateTime fromDate, LocalDateTime toDate,
			String barcode) {
		log.debug("debugging ProductBundleVo");
		List<ProductBundle> bundles = new ArrayList<>();
		Boolean status = Boolean.TRUE;
		if(fromDate!=null) {
			bundles=productBundleRepo.findByCreatedDateAndStatus(fromDate,status);
		} else if(fromDate!=null &&toDate!=null&&barcode=="") {
			bundles=productBundleRepo.findByCreatedDateBetweenAndStatusOrderByLastModifiedDate(fromDate,status,toDate);
		} else if(fromDate!=null && toDate!=null && barcode!=null) {
			bundles=productBundleRepo.findByCreatedDateBetweenAndStatusAndProductTextiles_BarcodeOrderByLastModifiedDateAsc(fromDate,status,barcode,toDate);
		} else if((barcode!=null||(barcode.isEmpty()))) {
			bundles=productBundleRepo.findByProductTextiles_BarcodeAndStatus(barcode,status);
		} else {
			bundles= productBundleRepo.findByStatus(status);	
		}
		List<ProductBundleVo> productBundleVo= productBundleMapper.EntityToVo(bundles);
		productBundleVo.stream().forEach(bundleVo -> {
			bundleVo.getProductTextiles().stream().forEach(productTextile->{
				productTextile=productTextileRepo.findBySellingTypeCode(ProductEnum.PRODUCTBUNDLE);
				bundleVo.setValue(bundleVo.getBundleQuantity()*productTextile.getItemMrp());
			});
		});
		log.info("after fetching all bundle details:" + bundles.toString());
		return productBundleVo;
	}

	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	public String updateProductBundle(ProductBundleVo productBundleVo) {
		log.debug("debugging updateProductBundle:" + productBundleVo);
		Optional<ProductBundle> productBundleOpt = productBundleRepo.findById(productBundleVo.getId());
		if (!productBundleOpt.isPresent()) {
			throw new RecordNotFoundException("bundle data is  not found with id: " + productBundleVo.getId());
		}
		ProductBundle productBundle = productBundleMapper.VoToEntity(productBundleVo);
		productBundle.setId(productBundleVo.getId());
		ProductBundle productBundleUpdate = productBundleRepo.save(productBundle);
		log.info("after updating bundle details:" + productBundleUpdate);
		return "after updated bundle successfully:" + productBundleVo.toString();
	}

	@Override
	public ProductBundleVo deleteProductBundle(Long id) {
		log.debug("debugging deleteProductBundle:" + id);
		Optional<ProductBundle> productBundleOpt = productBundleRepo.findById(id);
		if (!(productBundleOpt.isPresent())) {
			throw new RecordNotFoundException("bundle not found with id: " + id);
		}
		productBundleOpt.get().setStatus(Boolean.FALSE);
		
		//productBundleRepo.delete(productBundleOpt.get());
		log.info("after deleting bundle details:" + id);
		ProductBundleVo productBundleVo=productBundleMapper.EntityToVo(productBundleRepo.save(productBundleOpt.get()));
		return productBundleVo;
	}
}
