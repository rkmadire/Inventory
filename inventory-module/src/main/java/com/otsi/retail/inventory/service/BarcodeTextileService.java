package com.otsi.retail.inventory.service;

import com.otsi.retail.inventory.vo.BarcodeTextileVo;

public interface BarcodeTextileService {

	String saveBarcodeTextile(BarcodeTextileVo barcodeTextileVo);

	BarcodeTextileVo getBarcodeTextile(String barcode);

}
