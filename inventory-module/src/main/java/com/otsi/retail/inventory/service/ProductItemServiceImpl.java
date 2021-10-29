package com.otsi.retail.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.ProductItemAvEnum;
import com.otsi.retail.inventory.exceptions.RecordNotFoundException;
import com.otsi.retail.inventory.mapper.ProductItemMapper;
import com.otsi.retail.inventory.model.ProductImage;
import com.otsi.retail.inventory.model.ProductInventory;
import com.otsi.retail.inventory.model.ProductItem;
import com.otsi.retail.inventory.model.ProductItemAv;
import com.otsi.retail.inventory.repo.ProductImageRepo;
import com.otsi.retail.inventory.repo.ProductInventoryRepo;
import com.otsi.retail.inventory.repo.ProductItemAvRepo;
import com.otsi.retail.inventory.repo.ProductItemRepo;
import com.otsi.retail.inventory.vo.ProductItemVo;

@Component
public class ProductItemServiceImpl implements ProductItemService {

	private Logger log = LoggerFactory.getLogger(ProductItemServiceImpl.class);

	@Autowired
	private ProductImageRepo productImageRepo;

	@Autowired
	private ProductItemAvRepo productItemAvRepo;

	@Autowired
	private ProductItemRepo productItemRepo;

	@Autowired
	private ProductInventoryRepo productInventoryRepo;

	@Autowired
	private ProductItemMapper productItemMapper;

	@Override
	public String createProduct(ProductItemVo vo) {
		log.debug("debugging createInventory");
		ProductItem productItem = productItemMapper.VoToEntity(vo);
		ProductItem saveProductItem = productItemRepo.save(productItem);
		saveAVValues(vo, saveProductItem);
		List<ProductImage> listImages = new ArrayList<>();
		List<ProductImage> productImage = vo.getProductImage();
		if (vo.getProductImage() != null) {
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
		}
		ProductInventory prodInv = new ProductInventory();
		prodInv.setProductItem(saveProductItem);
		prodInv.setCreationDate(LocalDate.now());
		prodInv.setLastModified(LocalDate.now());
		prodInv.setStockvalue(vo.getStockValue());
		ProductInventory prodInvSave = productInventoryRepo.save(prodInv);

		log.warn("we are checking if product item is saved...");
		log.info("saving product item  details");
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
		log.debug("debugging getProductByProductId:" + productItemId);
		Optional<ProductItem> productItem = productItemRepo.findByProductItemId(productItemId);
		if (!(productItem.isPresent())) {
			throw new RecordNotFoundException("product record is not found");

		} else {

			ProductItemVo vo = productItemMapper.EntityToVo(productItem.get());
			log.warn("we are checking if product item is fetching...");
			log.info("after fetching product item details:" + productItemId);
			return vo;
		}
	}

	@Override
	public List<ProductItemVo> getAllProducts(ProductItemVo vo) {
		log.debug("debugging getAllProducts()");
		List<ProductItem> prodItemDetails = new ArrayList<>();
		// List<ProductItem> prodItemDetails = inventoryRepo.findAll();

		/*
		 * using dates
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getProductItemId() == null) {
			prodItemDetails = productItemRepo.findByCreationDateBetweenOrderByLastModifiedDateAsc(vo.getFromDate(),
					vo.getToDate());

			if (prodItemDetails.isEmpty()) {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		/*
		 * using dates and productItemId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getProductItemId() != null) {
			Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(vo.getProductItemId());
			if (prodOpt.isPresent()) {
				prodItemDetails = productItemRepo.findByCreationDateBetweenAndProductItemIdOrderByLastModifiedDateAsc(
						vo.getFromDate(), vo.getToDate(), vo.getProductItemId());
			} else {
				log.error("No record found with given productItemId");
				throw new RecordNotFoundException("No record found with given productItemId");
			}

		}

		List<ProductItemVo> productList = productItemMapper.EntityToVo(prodItemDetails);
		log.warn("we are checking if product item is fetching...");
		log.info("after fetching all product item  details:" + productList.toString());
		return productList;
	}

	@Override
	public ProductItemVo getProductByName(String name) {
		log.debug("debugging getProductByProductId:" + name);
		Optional<ProductItem> productItemName = productItemRepo.findByName(name);
		if (!(productItemName.isPresent())) {
			throw new RecordNotFoundException("product record is not found");

		} else {

			ProductItemVo vo = productItemMapper.EntityToVo(productItemName.get());
			log.warn("we are checking if product item is fetching...");
			log.info("after fetching product item detailswith name:" + name);
			return vo;
		}
	}

	@Override
	public ProductItemVo getBarcodeId(Long barcodeId) {
		log.debug("debugging getProductByProductId:" + barcodeId);
		Optional<ProductItem> barOpt = productItemRepo.findByBarcodeId(barcodeId);
		if (!(barOpt.isPresent())) {
			throw new RecordNotFoundException("barcode record is not found");

		} else {

			ProductItemVo vo = productItemMapper.EntityToVo(barOpt.get());
			log.warn("we are checking if product item is fetching...");
			log.info("after fetching product item details:" + barcodeId);
			return vo;
		}
	}

	@Override
	public List<ProductItemVo> getAllBarcodes(ProductItemVo vo) {
		log.debug("debugging getAllBarcodes()");
		List<ProductItem> barcodeDetails = new ArrayList<>();

		/*
		 * using dates
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() == null) {
			barcodeDetails = productItemRepo.findByCreationDateBetweenOrderByLastModifiedDateAsc(vo.getFromDate(),
					vo.getToDate());

			if (barcodeDetails.isEmpty()) {
				log.error("No record found with given information");
				throw new RecordNotFoundException("No record found with given information");
			}
		}

		/*
		 * using dates and barcodeId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() != null) {
			Optional<ProductItem> barOpt = productItemRepo.findByBarcodeId(vo.getBarcodeId());
			if (barOpt.isPresent()) {
				barcodeDetails = productItemRepo.findByCreationDateBetweenAndBarcodeIdOrderByLastModifiedDateAsc(
						vo.getFromDate(), vo.getToDate(), vo.getBarcodeId());
			} else {
				log.error("No record found with given barcodeId");
				throw new RecordNotFoundException("No record found with given barcodeId");
			}

		}

		List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails);
		log.warn("we are checking if barcode is fetching...");
		log.info("after fetching all barcode details:" + barcodeList.toString());
		return barcodeList;
	}

	@Override
	public String updateProduct(ProductItemVo vo) {

		Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(vo.getProductItemId());
		if (!prodOpt.isPresent()) {
			throw new RecordNotFoundException("product item id is not found");
		}
		ProductInventory item = prodOpt.get().getProductInventory();
		if (item == null) {
			throw new RecordNotFoundException("product inventory is not found");
		}
		ProductInventory prodInventory = new ProductInventory();
		prodInventory.setProductInventoryId(item.getProductInventoryId());
		prodInventory.setStockvalue(vo.getStockValue());
		prodInventory.setLastModified(LocalDate.now());
		prodInventory.setProductItem(item.getProductItem());
		productInventoryRepo.save(prodInventory);
		return "updated product successfully";
	}
}
