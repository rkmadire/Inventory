package com.otsi.retail.inventory.service;

import java.util.Optional;

import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;

public interface BarcodeTextileService {

	String saveBarcodeTextile(BarcodeTextileVo barcodeTextileVo);

	BarcodeTextileVo getBarcodeTextile(String barcode);

}
