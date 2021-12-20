package com.otsi.retail.inventory.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otsi.retail.inventory.commons.NatureOfTransaction;
import com.otsi.retail.inventory.commons.ProductItemAvEnum;
import com.otsi.retail.inventory.exceptions.DuplicateRecordException;
import com.otsi.retail.inventory.exceptions.InvalidDataException;
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
			throw new DuplicateRecordException("barcodeId is already exists:" + vo.getBarcodeId());
		}
		ProductItem productItem = productItemMapper.VoToEntity(vo);
		if (!vo.getIsBarcode()) {

			productItem.setBarcodeId(getSaltString());
		}
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
		prodInv.setBarcodeId(productItem.getBarcodeId());
		prodInv.setEffectingTable("Product Item Table");
		prodInv.setEffectingTableID(saveProductItem.getProductItemId());
		prodInv.setMasterFlag(true);
		prodInv.setNatureOfTransaction(NatureOfTransaction.PURCHASE.getName());
		prodInv.setComment("Newly Inserted Table");
		prodInv.setCreationDate(LocalDate.now());
		prodInv.setStoreId(vo.getStoreId());
		prodInv.setLastModified(LocalDate.now());
		prodInv.setStockvalue(vo.getStockValue());
		ProductInventory prodInvSave = productInventoryRepo.save(prodInv);

		log.warn("we are checking if barcode  is saved...");
		log.info("saving barcode details");
		return "barcode saved successfully:" + prodInvSave.getProductItem().getBarcodeId();

	}

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	private void saveAVValues(ProductItemVo vo, ProductItem savedproductItem) {

		ProductItemAv productItemAv = null;

		if (vo.getColour() != null) {
			productItemAv = new ProductItemAv();
			productItemAv.setName(ProductItemAvEnum.COLOR.geteName());
			productItemAv.setType(ProductItemAvEnum.COLOR.getId());
			productItemAv.setStringValue(vo.getColour());
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
	public ProductItemVo getProductByProductId(Long productItemId, Long storeId) {
		log.debug("debugging getProductByProductId:" + productItemId);
		List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(storeId);
		if (storeOpt != null) {
			Optional<ProductItem> productItem = productItemRepo.findByProductItemId(productItemId);
			if (!(productItem.isPresent())) {
				throw new RecordNotFoundException("product record is not found");

			} else {

				ProductItemVo vo = productItemMapper.EntityToVo(productItem.get());
				log.warn("we are checking if product item is fetching...");
				log.info("after fetching product item details:" + productItemId);
				return vo;
			}
		} else {
			throw new RecordNotFoundException("No record found with storeId");
		}
	}

	@Override
	public List<ProductItemVo> getAllProducts(ProductItemVo vo) {
		log.debug("debugging getAllProducts()");
		List<ProductItem> prodItemDetails = new ArrayList<>();
		
		List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());

		/*
		 * using dates and storeId
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getProductItemId() == null
				&& vo.getStoreId() != null) {

			if (storeOpt != null) {

				prodItemDetails = productItemRepo
						.findByCreationDateBetweenAndStoreIdOrderByLastModifiedDateAsc(vo.getFromDate(),
								vo.getToDate(), vo.getStoreId());

				if (prodItemDetails.isEmpty()) {
					log.error("No record found with given information");
					throw new RecordNotFoundException("No record found with given information");
				}
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}

		/*
		 * using dates and productItemId and storeId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getProductItemId() != null
				&& vo.getStoreId() != null) {

			if (storeOpt != null) {
				Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(vo.getProductItemId());
				if (prodOpt.isPresent()) {
					prodItemDetails = productItemRepo
							.findByCreationDateBetweenAndProductItemIdAndStoreIdOrderByLastModifiedDateAsc(
									vo.getFromDate(), vo.getToDate(), vo.getProductItemId(), vo.getStoreId());
				} else {
					log.error("No record found with given productItemId");
					throw new RecordNotFoundException("No record found with given productItemId");
				}
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}
		/*
		 * using productItemId and storeId
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && vo.getProductItemId() != null
				&& vo.getStoreId() != null) {

			if (storeOpt != null) {
				Optional<ProductItem> barOpt = productItemRepo.findByProductItemId(vo.getProductItemId());
				if (!barOpt.isPresent()) {
					throw new RecordNotFoundException("barcode record is not found");

				} else {

					prodItemDetails.add(barOpt.get());
					List<ProductItemVo> productList = productItemMapper.EntityToVo(prodItemDetails);
					return productList;

				}
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}
		/*
		 * using storeId
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && vo.getProductItemId() == null
				&& vo.getStoreId() != null) {

			if (storeOpt != null) {
				//List<ProductItem> prodItemDetails1 = productItemRepo.findByStoreIdIn(vo.getStoreId());
				List<ProductItemVo> productList = productItemMapper.EntityToVo(storeOpt);
				return productList;
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}
		/*
		 * values with empty string
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && vo.getProductItemId() == null
				&& vo.getStoreId() == null) {
			List<ProductItem> prodItemDetails1 = productItemRepo.findAll();
			List<ProductItemVo> productList = productItemMapper.EntityToVo(prodItemDetails1);
			return productList;
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
	public ProductItemVo getProductByName(String name, Long storeId) {
		log.debug("debugging getProductByProductId:" + name);
		List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(storeId);
		if (storeOpt != null) {
			Optional<ProductItem> productItemName = productItemRepo.findByName(name);
			if (!(productItemName.isPresent())) {
				throw new RecordNotFoundException("product record is not found");

			} else {

				ProductItemVo vo = productItemMapper.EntityToVo(productItemName.get());
				log.warn("we are checking if product item is fetching...");
				log.info("after fetching product item detailswith name:" + name);
				return vo;
			}
		} else {
			throw new RecordNotFoundException("No record found with storeId:" + storeId);
		}
	}

	@Override
	public ProductItemVo getBarcodeId(String barcodeId, Long storeId) {
		log.debug("debugging getProductByProductId:" + barcodeId);
		List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(storeId);
		if (storeOpt != null) {
			ProductItem barOpt = productItemRepo.findByBarcodeId(barcodeId);
			if (barOpt == null) {
				throw new RecordNotFoundException("barcode record is not found");
			} else {

				ProductItemVo vo = productItemMapper.EntityToVo(barOpt);
				log.warn("we are checking if product item is fetching...");
				log.info("after fetching product item details:" + barcodeId);
				return vo;
			}
		} else {
			throw new RecordNotFoundException("No record found with storeId");
		}

	}

	@Override
	public List<ProductItemVo> getAllBarcodes(ProductItemVo vo) {
		log.debug("debugging getAllBarcodes()");
		List<ProductItem> barcodeDetails = new ArrayList<>();
		
		/*
		 * using dates and storeId
		 */
		if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() == "" && vo.getStoreId() != null) {
			List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());
			if (storeOpt != null) {
				barcodeDetails = productItemRepo.findByCreationDateBetweenAndStoreIdOrderByLastModifiedDateAsc(
						vo.getFromDate(), vo.getToDate(), vo.getStoreId());

				if (barcodeDetails.isEmpty()) {
					log.error("No record found with given information");
					throw new RecordNotFoundException("No record found with given information");
				}
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}

		/*
		 * using dates and barcodeId and storeId
		 */
		else if (vo.getFromDate() != null && vo.getToDate() != null && vo.getBarcodeId() != null
				&& vo.getStoreId() != null) {
			List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());
			if (storeOpt != null) {
				ProductItem barOpt = productItemRepo.findByBarcodeId(vo.getBarcodeId());
				if (barOpt != null) {
					barcodeDetails = productItemRepo
							.findByCreationDateBetweenAndBarcodeIdAndStoreIdOrderByLastModifiedDateAsc(
									vo.getFromDate(), vo.getToDate(), vo.getBarcodeId(), vo.getStoreId());
				} else {
					log.error("No record found with given barcodeId");
					throw new RecordNotFoundException("No record found with given barcodeId");
				}
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}

		/*
		 * values with empty string
		 */
		else if ((vo.getFromDate() == null) && (vo.getToDate() == null) && (vo.getBarcodeId() == "")
				&& (vo.getStoreId() == null)) {
			List<ProductItem> barcodeDetails1 = productItemRepo.findAll();;
			List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails1);
			return barcodeList;
		}
		/*
		 * using storeId
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && vo.getBarcodeId() == ""
				&& vo.getStoreId() != null) {
			List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());
			if (storeOpt != null) {
				List<ProductItem> barcodeDetails1 = productItemRepo.findAllByStoreId(vo.getStoreId());
				List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails1);
				return barcodeList;
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}
		/*
		 * using barcodeId and storeId
		 */
		else if (vo.getFromDate() == null && vo.getToDate() == null && (vo.getBarcodeId() != null)
				&& vo.getStoreId() != null) {
			List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());
			if (storeOpt != null) {
				ProductItem barOpt = productItemRepo.findByBarcodeId(vo.getBarcodeId());
				if (barOpt == null) {
					throw new RecordNotFoundException("barcode record is not found");
				} else {

					barcodeDetails.add(barOpt);
					List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails);
					return barcodeList;
				}
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		} else if (vo.getStoreId() != null) {
			List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());
			if (storeOpt != null) {
				List<ProductItem> barcodeDetails1 = productItemRepo.findAll();;
				List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails1);
				return barcodeList;
			} else {
				throw new RecordNotFoundException("No record found with storeId");
			}
		}

		List<ProductItemVo> barcodeList = productItemMapper.EntityToVo(barcodeDetails);
		log.warn("we are checking if barcode is fetching...");
		log.info("after fetching all barcode details:" + barcodeList.toString());
		return barcodeList;
	}

	@Override
	public String updateInventory(ProductItemVo vo) {
		List<ProductItem> storeOpt = productItemRepo.findAllByStoreId(vo.getStoreId());
		if (storeOpt != null) {
			ProductItem barOpt = productItemRepo.findByBarcodeId(vo.getBarcodeId());
			if (barOpt == null) {
				throw new RecordNotFoundException("barcode id is not found");
			}
			Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(barOpt.getProductItemId());
			ProductInventory item = prodOpt.get().getProductInventory();
			if (item == null) {
				throw new RecordNotFoundException("product inventory is not found");
			}
			ProductInventory prodInventory = new ProductInventory();
			prodInventory.setProductInventoryId(item.getProductInventoryId());
			prodInventory.setStockvalue(vo.getStockValue());
			prodInventory.setCreationDate(LocalDate.now());
			prodInventory.setLastModified(LocalDate.now());
			prodInventory.setProductItem(item.getProductItem());
			prodInventory.setEffectingTable("Product Item Table");
			prodInventory.setEffectingTableID(item.getEffectingTableID());
			prodInventory.setBarcodeId(vo.getBarcodeId());
			prodInventory.setMasterFlag(true);
			prodInventory.setNatureOfTransaction(NatureOfTransaction.PURCHASE.getName());
			prodInventory.setComment("Product Item table");
			prodInventory.setStoreId(item.getStoreId());
			productInventoryRepo.save(prodInventory);
			return "updated inventory successfully:" + prodOpt.get().getProductItemId();
		} else {
			throw new RecordNotFoundException("No record found with storeId");
		}
	}
	@Override
	public String updateBarcode(ProductItemVo vo) {
		log.debug(" debugging updateBarcode:" + vo);
		if (vo.getProductItemId() == null) {
			throw new InvalidDataException("productItem record not found");
		}
		ProductItem dto = productItemRepo.findByBarcodeId(vo.getBarcodeId());
		if (dto == null) {
			log.error("Record Not Found");
			throw new RecordNotFoundException("barcode record not found");
		}
		Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(vo.getProductItemId());
		if (!prodOpt.isPresent()) {
			throw new RecordNotFoundException("productItem record not found");
		}

		ProductItem update = productItemMapper.VoToEntityUpdate(vo, dto);

		ProductItem save = productItemRepo.save(update);
		List<ProductItemAv> prodAvOpt = productItemAvRepo.findByProductItem(save);
		List<ProductItemAv> prodavs = new ArrayList<>();
		prodAvOpt.stream().forEach(y -> {

			y.setProductItemAvId(y.getProductItemAvId());
			if (y.getName().equalsIgnoreCase(ProductItemAvEnum.COLOR.geteName())) {
				y.setStringValue(vo.getColour());
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
		if (vo.getProductImage() != null) {
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
		}
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
	public String deleteBarcode(String barcodeId) {
		log.debug(" debugging deleteBarcode:" + barcodeId);
		ProductItem barOpt = productItemRepo.findByBarcodeId(barcodeId);
		if (barOpt == null) {
			throw new RecordNotFoundException("barcode details not found with id: " + barcodeId);
		}
		Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(barOpt.getProductItemId());
		if (!prodOpt.isPresent()) {
			log.error("barcode details not found with id");
			throw new RecordNotFoundException("product details not found with id: " + barOpt.getProductItemId());
		} else {
			productImageRepo.deleteAll(prodOpt.get().getProductImage());
			productInventoryRepo.delete(prodOpt.get().getProductInventory());
			productItemRepo.delete(prodOpt.get());
			log.warn("we are checking if barcode is deleted based on id...");
			log.info("barcode deleted succesfully:" + barcodeId);
			return "barcode deleted successfully with id:" + barcodeId;
		}
	}

	@Override
	public String fromNewSaleForRetail(Map<String, Integer> map) {
		String barcodeId = null;
		Integer qty = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("barcode")) {
				barcodeId = entry.getValue().toString();

			}
			if (entry.getKey().equalsIgnoreCase("qty")) {
				qty = entry.getValue();

			}
		}
		ProductItem barOpt = productItemRepo.findByBarcodeId(barcodeId);
		if (barOpt == null) {
			throw new RecordNotFoundException("barcode id is not found");
		}
		Optional<ProductItem> prodOpt = productItemRepo.findByProductItemId(barOpt.getProductItemId());
		ProductInventory item = prodOpt.get().getProductInventory();
		if (item == null) {
			throw new RecordNotFoundException("product inventory is not found");
		}
		ProductInventory prodInventory = new ProductInventory();
		prodInventory.setProductInventoryId(item.getProductInventoryId());
		prodInventory.setStockvalue(Math.abs(barOpt.getProductInventory().getStockvalue() - qty));
		prodInventory.setLastModified(LocalDate.now());
		prodInventory.setProductItem(item.getProductItem());
		productInventoryRepo.save(prodInventory);

		return "stock updated successfully";

	}

	@Override
	public String saveProductList(List<ProductItemVo> productItemVos) {
		productItemVos.stream().forEach(v -> {
			createBarcode(v);
		});
		log.info("after saving all debitnotes:" + productItemVos.toString());
		return "saving list of product details...";
	}

}
