package com.otsi.retail.inventory.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.ProductItemAvEnum;
import com.otsi.retail.inventory.model.Domaindata;
import com.otsi.retail.inventory.model.ProductImage;
import com.otsi.retail.inventory.model.ProductInventory;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductItemAv;
import com.otsi.retail.inventory.model.Store;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Component
public class ProductItemMapper {

	@Autowired
	private DomainDataMapper domainDataMapper;

	@Autowired
	private StoreMapper storeMapper;

	public ProductItemVo EntityToVo(ProductItem dto) {
		ProductItemVo vo = new ProductItemVo();
		vo.setProductItemId(dto.getProductItemId());
		vo.setCostPrice(dto.getCostPrice());
		vo.setFromDate(dto.getCreationDate());
		vo.setToDate(dto.getLastModifiedDate());
		vo.setDefaultImage(dto.getDefaultImage());
		vo.setListPrice(dto.getListPrice());
		vo.setStatus(dto.getStatus());
		vo.setStock(dto.getStock());
		vo.setTitle(dto.getTitle());
		vo.setTyecode(dto.getTyecode());
		vo.setName(dto.getName());
		vo.setEmpId(dto.getEmpId());
		vo.setCostPrice(dto.getCostPrice());
		vo.setUom(dto.getUom());
		vo.setDomainDataId(domainDataMapper.EntityToVo(dto.getDomainData()).getDomainDataId());
		vo.setStoreId(storeMapper.EntityToVo(dto.getStore()).getStoreId());
		vo.setBarcodeId(dto.getBarcodeId());
		List<ProductImage> listImages = new ArrayList<>();
		List<ProductImage> productImage = dto.getProductImage();
		productImage.forEach(x -> {
			ProductImage image = new ProductImage();
			image.setProductImageId(x.getProductImageId());
			image.setImage(x.getImage());
			image.setPIUID(x.getPIUID());
			image.setCreationDate(LocalDate.now());
			image.setLastModified(LocalDate.now());
			listImages.add(image);

		});
		vo.setProductImage(listImages);
		ProductInventory prodInv = new ProductInventory();
		prodInv.setStockvalue(dto.getProductInventory().getStockvalue());
		vo.setStockValue(prodInv.getStockvalue());
		List<ProductItemAv> productAv = dto.getProductItemAvId();
		productAv.stream().forEach(y -> {
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.COLOR.geteName())) {
				vo.setColor(y.getStringValue());
			}
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.LENGTH.geteName())) {
				vo.setLength(y.getIntValue());
			}
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.PRODUCT_VALIDITY.geteName())) {
				vo.setProductValidity(y.getDateValue());
			}

		});
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<ProductItemVo> EntityToVo(List<ProductItem> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public ProductItem VoToEntity(ProductItemVo vo) {

		Random rand = new Random();
		ProductItem dto = new ProductItem();
		dto.setProductItemId(vo.getProductItemId());
		dto.setName(vo.getName());
		dto.setCostPrice(vo.getCostPrice());
		dto.setCreationDate(LocalDate.now());
		dto.setLastModifiedDate(LocalDate.now());
		dto.setDefaultImage(vo.getDefaultImage());
		dto.setListPrice(vo.getListPrice());
		dto.setStatus(vo.getStatus());
		dto.setStock(vo.getStock());
		dto.setEmpId(vo.getEmpId());
		dto.setTitle(vo.getTitle());
		dto.setTyecode(vo.getTyecode());
		dto.setBarcodeId(rand.nextInt());
		dto.setUom(vo.getUom());
		Domaindata data = new Domaindata();
		data.setDomainDataId(vo.getDomainDataId());
		dto.setDomainData(data);

		Store store = new Store();
		store.setStoreId(vo.getStoreId());
		dto.setStore(store);
		return dto;

	}
	/*
	 * to convert list vo's to dto's
	 */

	public List<ProductItem> VoToEntity(List<ProductItemVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
