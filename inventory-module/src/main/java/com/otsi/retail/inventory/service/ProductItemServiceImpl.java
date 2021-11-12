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
import com.otsi.retail.inventory.exceptions.DuplicateRecordException;
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
	public String createBarcode(ProductItemVo vo) {
		log.debug("debugging createInventory");
		if (productItemRepo.existsByName(vo.getName())) {
			throw new DuplicateRecordException("product name is already exists:" + vo.getName());
		}
		if (productItemRepo.existsByBarcodeId(vo.getBarcodeId())) {
			throw new DuplicateRecordException("barcode id is already exists:" + vo.getBarcodeId());
		}
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

		log.warn("we are checking if barcode  is saved...");
		log.info("saving barcode details");
		return "barcode saved successfully:"+prodInvSave.getProductItem().getBarcodeId();

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

		} else {
			List<ProductItem> prodItemDetails1 = productItemRepo.findAll();
			List<ProductItemVo> productList = productItemMapper.EntityToVo(prodItemDetails1);
			return productList;
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
	public ProductItemVo getBarcodeId(int barcodeId) {
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
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() == 0) {
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
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() != 0) {
			Optional<ProductItem> barOpt = productItemRepo.findByBarcodeId(vo.getBarcodeId());
			if (barOpt.isPresent()) {
				barcodeDetails = productItemRepo.findByCreationDateBetweenAndBarcodeIdOrderByLastModifiedDateAsc(
						vo.getFromDate(), vo.getToDate(), vo.getBarcodeId());
			} else {
				log.error("No record found with given barcodeId");
				throw new RecordNotFoundException("No record found with given barcodeId");
			}

		} else {
			List<ProductItem> barcodeDetails1 = productItemRepo.findAll();
			List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails1);
			return barcodeList;
		}

		List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails);
		log.warn("we are checking if barcode is fetching...");
		log.info("after fetching all barcode details:" + barcodeList.toString());
		return barcodeList;
	}

	@Override
	public String updateInventory(ProductItemVo vo) {

		Optional<ProductItem> barOpt = productItemRepo.findByBarcodeId(vo.getBarcodeId());
		if (!barOpt.isPresent()) {
			throw new RecordNotFoundException("barcode id is not found");
		}
		Optional<ProductItem> prodOpt=productItemRepo.findByProductItemId(barOpt.get().getProductItemId());
		ProductInventory item = prodOpt.get().getProductInventory();
		if (item == null) {
			throw new RecordNotFoundException("product inventory is not found");
		}
		ProductInventory prodInventory = new ProductInventory();
		prodInventory.setProductInventoryId(item.getProductInventoryId());
		prodInventory.setStockvalue(vo.getStockValue());
		prodInventory.setCreationDate(item.getCreationDate());
		prodInventory.setLastModified(LocalDate.now());
		prodInventory.setProductItem(item.getProductItem());
		productInventoryRepo.save(prodInventory);
		return "updated inventory successfully:"+vo.getBarcodeId();
	}

	@Override
	public String updateBarcode(ProductItemVo vo) {
		log.debug(" debugging updateBarcode:" + vo);
		Optional<ProductItem> dto = productItemRepo.findByBarcodeId(vo.getBarcodeId());
		if (!dto.isPresent()) {
			log.error("Record Not Found");
			throw new RecordNotFoundException("barcode record not found");
		}
		Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(dto.get().getProductItemId());
		if (!prodOpt.isPresent()) {
			throw new RecordNotFoundException("productItem record not found");
		}
		ProductItem update = productItemMapper.VoToEntity(vo);
		ProductItem save = productItemRepo.save(update);
		List<ProductItemAv> prodAvOpt = productItemAvRepo.findByProductItem(save);
		List<ProductItemAv> prodavs = new ArrayList<>();
		prodAvOpt.stream().forEach(y -> {

			y.setProductItemAvId(y.getProductItemAvId());
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.COLOR.geteName())) {
				y.setStringValue(vo.getColor());
			}
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.LENGTH.geteName())) {
				y.setIntValue(vo.getLength());
			}
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.PRODUCT_VALIDITY.geteName())) {
				y.setDateValue(vo.getProductValidity());
			}

			y.setProductItem(save);

			prodavs.add(y);
		});
		List<ProductItemAv> pav = productItemAvRepo.saveAll(prodavs);
		update.setProductItemAvId(pav);

		List<ProductImage> listImages = new ArrayList<>();
		List<ProductImage> productImage = vo.getProductImage();

		productImage.forEach(x -> {
			ProductImage image = new ProductImage();
			image.setProductImageId(x.getProductImageId());
			image.setImage(x.getImage());
			image.setPIUID(x.getPIUID());
			image.setCreationDate(LocalDate.now());
			image.setLastModified(LocalDate.now());
			image.setProductItem(save);
			listImages.add(image);

		});
		List<ProductImage> s = productImageRepo.saveAll(listImages);
		update.setProductImage(s);
		Optional<ProductInventory> prodOp = productInventoryRepo.findByProductItem(save);
		ProductInventory prodInv = prodOp.get();
		prodInv.setProductItem(save);
		prodInv.setLastModified(LocalDate.now());
		prodInv.setStockvalue(vo.getStockValue());
		ProductInventory prodInvSave = productInventoryRepo.save(prodInv);
		update.setProductInventory(prodInvSave);
		log.info("barcode updated successsfully:" + vo.toString());
		return "barcode updated successsfully:" + vo.getBarcodeId();
	}

	@Override
	public String deleteBarcode(int barcodeId) {
		log.debug(" debugging deleteBarcode:" + barcodeId);
		Optional<ProductItem> barOpt=productItemRepo.findByBarcodeId(barcodeId);
		if(!barOpt.isPresent()) {
			throw new RecordNotFoundException("barcode details not found with id: " + barcodeId);
		}
		Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(barOpt.get().getProductItemId());
		if (!prodOpt.isPresent()) {
			log.error("barcode details not found with id");
			throw new RecordNotFoundException("product details not found with id: " + barOpt.get().getProductItemId());
		} else {
			productImageRepo.deleteAll(prodOpt.get().getProductImage());
			productInventoryRepo.delete(prodOpt.get().getProductInventory());
			productItemRepo.delete(prodOpt.get());
			log.warn("we are checking if barcode is deleted based on id...");
			log.info("barcode deleted succesfully:" + barcodeId);
			return "barcode deleted successfully with id:" + barcodeId;
		}
	}
}
