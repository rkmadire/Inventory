package com.otsi.retail.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;

@Service
public interface ProductTextileService {

	ProductTextileVo getProductTextile(Long productTextileId);

	String addBarcodeTextile(BarcodeTextileVo textileVo);

	String updateBarcodeTextile(BarcodeTextileVo vo);

	String deleteBarcodeTextile(Long barcodeTextileId);
	
	BarcodeTextileVo getBarcodeTextile(String barcode);

	List<BarcodeTextileVo> getAllBarcodes(SearchFilterVo vo);

}
