package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.vo.BarcodeVo;

@Service
public interface BarcodeService {

	String createBarcode(BarcodeVo vo);

	Optional<Barcode> getBarcode(String barcode);

	List<BarcodeVo> getAllBarcodes();

	String updateBarcode(Long barcodeId, BarcodeVo barcodeVo);

	String deleteBarcode(Long barcodeId);

	//CatalogCategoriesVo getCatalogsFromCatalog(Long id);

}
