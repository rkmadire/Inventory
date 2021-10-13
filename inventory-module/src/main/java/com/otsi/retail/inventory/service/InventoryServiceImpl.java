package com.otsi.retail.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.ProductItemAvEnum;
import com.otsi.retail.inventory.exceptions.InvalidDataException;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.InventoryMapper;
import com.otsi.retail.inventory.model.ProductImage;
import com.otsi.retail.inventory.model.ProductInventory;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductItemAv;
import com.otsi.retail.inventory.repo.BarcodeRepo;
import com.otsi.retail.inventory.repo.InventoryRepo;
import com.otsi.retail.inventory.repo.ProductImageRepo;
import com.otsi.retail.inventory.repo.ProductInventoryRepo;
import com.otsi.retail.inventory.repo.ProductItemAvRepo;
import com.otsi.retail.inventory.vo.CatalogVo;
import com.otsi.retail.inventory.vo.ProductItemAvVo;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Component
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private ProductImageRepo productImageRepo;

	@Autowired
	private ProductItemAvRepo productItemAvRepo;

	@Autowired
	private InventoryRepo inventoryRepo;

	@Autowired
	private ProductInventoryRepo productInventoryRepo;

	@Autowired
	private InventoryMapper inventoryMapper;

	@Autowired
	private BarcodeService barcodeService;

	@Override
	public String createInventory(ProductItemVo vo) {
		if (vo.getBarcode() == null&&vo.getStore()==null&&vo.getDomainData()==null) {
			throw new InvalidDataException("please enter valid data");

		ProductItem productItem = inventoryMapper.VoToEntity(vo);

		ProductItem item = inventoryRepo.findByNameAndUomAndCostPriceAndListPrice(vo.getName(), vo.getUom(),
				vo.getCostPrice(), vo.getListPrice());
		if (item != null) {
			ProductInventory prodInv = item.getProductInventory();

			// int ValuIncrease = item.getProductInventory().getStockvalue() + 1;
			prodInv.setProductInventoryId(item.getProductInventory().getProductInventoryId());
			prodInv.setStockvalue(item.getProductInventory().getStockvalue() + 1);
			prodInv.setLastModified(LocalDate.now());
			productInventoryRepo.save(prodInv);

		} else {
			ProductItem saveProductItem = inventoryRepo.save(productItem);
			ProductInventory prodInv = vo.getProductInventory();
			prodInv.setProductItem(saveProductItem);
			saveAVValues(vo, saveProductItem);

			List<ProductImage> listImages = new ArrayList<>();
			List<ProductImage> productImage = vo.getProductImage();
			productImage.forEach(x -> {
				ProductImage image = new ProductImage();
				image.setImage(x.getImage());
				image.setPIUID(x.getPIUID());
				image.setCreationDate(LocalDate.now());
				image.setLastModified(LocalDate.now());
				image.setProductItem(saveProductItem);
				listImages.add(image);

			});
			List<ProductImage> s = productImageRepo.saveAll(listImages);
			productItem.setProductImage(s);

			prodInv.setProductInventoryId(vo.getProductInventory().getProductInventoryId());
			prodInv.setCreationDate(LocalDate.now());
			prodInv.setLastModified(LocalDate.now());
			prodInv.setStockvalue(vo.getProductInventory().getStockvalue());
			ProductInventory prodInvSave = productInventoryRepo.save(prodInv);
			productItem.setProductInventory(prodInvSave);

		}

		/*
		 * ProductInventory prodInvSave = productInventoryRepo.save(prodInv);
		 * productItem.setProductInventory(prodInvSave);
		 * 
		 * /* // ProductItem saveProductItem1 = inventoryRepo.save(productItem);
		 * 
		 * // productItem.setProductInventory(prodInvSave);
		 * 
		 * // inventoryRepo.save(productItem);
		 */
		return "created inventory successfully";
	}

	private void saveAVValues(ProductItemVo vo, ProductItem savedproductItem) {

		ProductItemAv productItemAv = null;

		if (vo.getColor() != null) {
			productItemAv = new ProductItemAv();
			productItemAv.setName(ProductItemAvEnum.COLOR.geteName());
			productItemAv.setType(ProductItemAvEnum.COLOR.getId());
			productItemAv.setStringValue(vo.getColor());
			saveToRepo(productItemAv, savedproductItem);

		}

		if (vo.getLength() != 0) {

			productItemAv = new ProductItemAv();
			productItemAv.setName(ProductItemAvEnum.LENGTH.geteName());
			productItemAv.setType(ProductItemAvEnum.LENGTH.getId());
			productItemAv.setIntValue(vo.getLength());
			saveToRepo(productItemAv, savedproductItem);
		}

		if (vo.getProductValidity() != null) {

			productItemAv = new ProductItemAv();
			productItemAv.setName(ProductItemAvEnum.PRODUCT_VALIDITY.geteName());
			productItemAv.setType(ProductItemAvEnum.PRODUCT_VALIDITY.getId());
			productItemAv.setDateValue(vo.getProductValidity());
			saveToRepo(productItemAv, savedproductItem);
		}
	}

	private void saveToRepo(ProductItemAv productItemAv, ProductItem savedProduct) {
		productItemAv.setLastModified(LocalDate.now());
		productItemAv.setProductItem(savedProduct);
		productItemAvRepo.save(productItemAv);
	}

	@Override
	public ProductItemVo getProductByProductId(Long productItemId) {

		Optional<ProductItem> productItem = inventoryRepo.findByProductItemId(productItemId);
		if (!(productItem.isPresent())) {
			throw new RecordNotFoundException("product record is not found");

		} else {

			ProductItemVo vo = inventoryMapper.EntityToVo(productItem.get());
			// vo.getBarcode().setDefaultCategoryId(barcodeService.getCatalogsFromCatalog(productItem.get().getBarcode().getDefaultCategoryId()));
			/*
			 * List<CatalogVo> catalogsFromCatalog =
			 * barcodeService.getCatalogsFromCatalog(productItem.get().getBarcode().
			 * getDefaultCategoryId());
			 * vo.getBarcode().setDefaultCategoryId(catalogsFromCatalog);
			 */
			/*
			 * if (productItem.isPresent()) { ProductItem productItem1 = productItem.get();
			 * 
			 * productItem1.getProductItemAvId().stream().forEach(x -> { if
			 * (x.getName().equalsIgnoreCase(ProductItemAvEnum.COLOR.geteName())) {
			 * vo.setColor(x.getStringValue()); } if
			 * (x.getName().equalsIgnoreCase(ProductItemAvEnum.LENGTH.geteName())) {
			 * vo.setLength(x.getIntValue()); } if
			 * (x.getName().equalsIgnoreCase(ProductItemAvEnum.PRODUCT_VALIDITY.geteName()))
			 * { vo.setProductValidity(x.getDateValue()); }
			 * 
			 * }); }
			 */

			return vo;
		}

	}

	@Override
	public List<ProductItemVo> getAllProducts() {
		List<ProductItem> productItem = inventoryRepo.findAll();
		List<ProductItemVo> productList = inventoryMapper.EntityToVo(productItem);

		/*
		 * productItem.stream().forEach(x -> {
		 * 
		 * List<ProductItemAv> listOfAv = x.getProductItemAvId();
		 * 
		 * listOfAv.stream().forEach(y -> { if
		 * (x.getName().equalsIgnoreCase(ProductItemAvEnum.COLOR.geteName())) {
		 * ((ProductItemVo) productList).setColor(y.getStringValue()); } if
		 * (x.getName().equalsIgnoreCase(ProductItemAvEnum.LENGTH.geteName())) {
		 * x.setLength(y.getIntValue()); } if
		 * (x.getName().equalsIgnoreCase(ProductItemAvEnum.PRODUCT_VALIDITY.geteName()))
		 * { x.setProductValidity(y.getDateValue()); }
		 * 
		 * });
		 * 
		 * vo.setName(y.getName()); vo.setColor(y.getStringValue());
		 * vo.setProductValidity(y.getDateValue());
		 * 
		 * 
		 * });
		 */

		return productList;
	}

}
