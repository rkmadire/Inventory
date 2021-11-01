package com.otsi.retail.inventory.service;

import java.util.List;

import com.otsi.retail.inventory.vo.BarcodeTextileVo;

public interface BarcodeTextileService {

	String saveBarcodeTextile(BarcodeTextileVo barcodeTextileVo);

	BarcodeTextileVo getBarcodeTextile(String barcode);

	List<BarcodeTextileVo> getAllBarcodes(BarcodeTextileVo vo);

}
