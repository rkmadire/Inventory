package com.otsi.retail.inventory.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.ProductItemAvEnum;
import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.model.ProductImage;
import com.otsi.retail.inventory.model.ProductInventory;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductItemAv;
import com.otsi.retail.inventory.service.BarcodeService;
import com.otsi.retail.inventory.service.ProductItemService;
import com.otsi.retail.inventory.vo.BarcodeVo;
import com.otsi.retail.inventory.vo.CatalogVo;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Component
public class BarcodeMapper {

	@Autowired
	private BarcodeService barcodeService;

	/*
	 * EntityToVo converts dto to vo
	 * 
	 */

	public BarcodeVo EntityToVo(Barcode dto) {
		BarcodeVo vo = new BarcodeVo();
		vo.setBarcodeId(dto.getBarcodeId());
		vo.setBarcode(dto.getBarcode());
		vo.setAttr1(dto.getAttr1());
		vo.setAttr2(dto.getAttr2());
		vo.setAttr3(dto.getAttr3());
		vo.setFromDate(dto.getCreationDate());
		vo.setToDate(dto.getLastModified());

		List<CatalogVo> catalogsFromCatalog = barcodeService.getCatalogsFromCatalog(dto.getDefaultCategoryId());
		vo.setDefaultCategoryId(catalogsFromCatalog);
		List<ProductItemVo> prodList = new ArrayList<>();
		List<ProductItem> prods = dto.getProductItem();
		prods.stream().forEach(p -> {
			ProductItemVo prodItem = new ProductItemVo();
			prodItem.setProductItemId(p.getProductItemId());
			prodItem.setFromDate(LocalDate.now());
			prodItem.setToDate(LocalDate.now());
			prodItem.setBarcodeId(p.getBarcode().getBarcodeId());
			prodItem.setDomainDataId(p.getDomainData().getDomainDataId());
			prodItem.setStoreId(p.getStore().getStoreId());
			prodItem.setDefaultImage(p.getDefaultImage());
			prodItem.setCostPrice(p.getCostPrice());
			prodItem.setListPrice(p.getListPrice());
			prodItem.setStatus(p.getStatus());
			prodItem.setTitle(p.getTitle());
			prodItem.setName(p.getName());
			prodItem.setProductImage(p.getProductImage());
			prodItem.setColor(p.getProductItemAvId().get(0).getStringValue());
			prodItem.setLength(p.getProductItemAvId().get(0).getIntValue());
			prodItem.setProductValidity(p.getProductItemAvId().get(0).getDateValue());

			p.getProductItemAvId().stream().forEach(x -> {
				if (x.getName().equalsIgnoreCase(ProductItemAvEnum.COLOR.geteName())) {
					prodItem.setColor(x.getStringValue());
				}
				if (x.getName().equalsIgnoreCase(ProductItemAvEnum.LENGTH.geteName())) {
					prodItem.setLength(x.getIntValue());
				}
				if (x.getName().equalsIgnoreCase(ProductItemAvEnum.PRODUCT_VALIDITY.geteName())) {
					prodItem.setProductValidity(x.getDateValue());
				}

			});

			ProductInventory prodInv = new ProductInventory();
			prodInv.setStockvalue(p.getProductInventory().getStockvalue());
			prodItem.setStockValue(prodInv.getStockvalue());
			prodItem.setStock(p.getStock());
			prodItem.setTyecode(p.getTyecode());
			prodItem.setUom(p.getUom());
			prodList.add(prodItem);
		});
		vo.setProductItem(prodList);
		return vo;

	}

	/*
	 * to convert list dto's to vo's
	 */

	public List<BarcodeVo> EntityToVo(List<Barcode> dtos) {
		return dtos.stream().map(dto -> EntityToVo(dto)).collect(Collectors.toList());

	}

	/*
	 * VoToEntity converts vo to dto
	 * 
	 */

	public Barcode VoToEntity(BarcodeVo vo) {
		Barcode dto = new Barcode();
		dto.setBarcodeId(vo.getBarcodeId());
		dto.setBarcode(vo.getBarcode());
		dto.setAttr1(vo.getAttr1());
		dto.setAttr2(vo.getAttr2());
		dto.setAttr3(vo.getAttr3());
		dto.setCreationDate(LocalDate.now());
		dto.setLastModified(LocalDate.now());
		return dto;

	}

	/*
	 * to convert list vo's to dto's
	 */

	public List<Barcode> VoToEntity(List<BarcodeVo> vos) {
		return vos.stream().map(vo -> VoToEntity(vo)).collect(Collectors.toList());

	}

}
