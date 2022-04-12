package com.otsi.retail.inventory.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import com.otsi.retail.inventory.model.ProductBundle;
import com.otsi.retail.inventory.vo.ProductBundleVo;


@Component
public class ProductBundleMapper {
	
	
	private ProductTextileMapper productTextileMapper;
	
	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public ProductBundleVo EntityToVo(ProductBundle productBundle) {
		ProductBundleVo productBundleVo=new ProductBundleVo();
		productBundleVo.setName(productBundle.getName());
		productBundleVo.setDescription(productBundle.getDescription());
		productBundleVo.setDomainId(productBundle.getDomainId());
		productBundleVo.setBundleQuantity(productBundle.getBundleQuantity());
		productBundleVo.setStatus(productBundle.getStatus());
		//productBundleVo.setProductTextiles(productTextileMapper.EntityToVo(productBundle.getProductTextiles()));
		return productBundleVo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<ProductBundleVo> EntityToVo(List<ProductBundle> productBundles) {
		return productBundles.stream().map(productBundle -> EntityToVo(productBundle)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public ProductBundle VoToEntity(ProductBundleVo productBundleVo) {
		ProductBundle productBundle = new ProductBundle();
		productBundle.setName(productBundleVo.getName());
		productBundle.setDescription(productBundleVo.getDescription());
		productBundle.setDomainId(productBundleVo.getDomainId());
		productBundle.setStatus(Boolean.TRUE);
		productBundle.setBundleQuantity(productBundleVo.getBundleQuantity());
		//productBundle.setProductTextiles(productTextileMapper.);
		return productBundle;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<ProductBundle> VoToEntity(List<ProductBundleVo> productBundleVos) {
		return productBundleVos.stream().map(productBundleVo -> VoToEntity(productBundleVo)).collect(Collectors.toList());

	}


}
