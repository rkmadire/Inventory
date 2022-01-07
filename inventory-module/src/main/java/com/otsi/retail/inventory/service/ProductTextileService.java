package com.otsi.retail.inventory.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.vo.AdjustmentsVo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.InventoryUpdateVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;

@Service
public interface ProductTextileService {

	ProductTextileVo getProductTextile(Long productTextileId);

	String addBarcodeTextile(BarcodeTextileVo textileVo);

	String updateBarcodeTextile(BarcodeTextileVo vo);

	String deleteBarcodeTextile(Long barcodeTextileId);

	BarcodeTextileVo getBarcodeTextile(String barcode, Long storeId);

	List<BarcodeTextileVo> getAllBarcodes(SearchFilterVo vo);

	String incrementQty(BarcodeTextileVo vo);

	String inventoryUpdateForTextile(List<InventoryUpdateVo> request);

	List<AdjustmentsVo> getAllAdjustments(AdjustmentsVo vo);

	String saveProductTextileList(List<BarcodeTextileVo> barcodeTextileVos);

	List<String> getAllColumns();

	List<String> getValuesFromProductTextileColumns(String enumName);

	List<String> getValuesFromBarcodeTextileColumns(String enumName);

}
