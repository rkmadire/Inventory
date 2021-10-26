package com.otsi.retail.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.model.Barcode;
import com.otsi.retail.inventory.vo.BarcodeVo;
import com.otsi.retail.inventory.vo.CatalogVo;

@Service
public interface BarcodeService {

	String createBarcode(BarcodeVo vo);

	BarcodeVo getBarcode(String barcode);

	/* List<BarcodeVo> getAllBarcodes(); */

	String deleteBarcode(Long barcodeId);

	List<CatalogVo> getCatalogsFromCatalog(Long id);

	List<BarcodeVo> getAllBarcodes(BarcodeVo vo);

}
