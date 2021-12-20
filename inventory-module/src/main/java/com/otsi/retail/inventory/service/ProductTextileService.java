package com.otsi.retail.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.otsi.retail.inventory.vo.AdjustmentsVo;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductItemVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;
import com.otsi.retail.inventory.vo.ProductTransactionVo;
import com.otsi.retail.inventory.vo.SearchFilterVo;
import com.otsi.retail.inventory.vo.UpdateInventoryRequest;

@Service
public interface ProductTextileService {

	ProductTextileVo getProductTextile(Long productTextileId);

	String addBarcodeTextile(BarcodeTextileVo textileVo);

	String updateBarcodeTextile(BarcodeTextileVo vo);

	String deleteBarcodeTextile(Long barcodeTextileId);

	BarcodeTextileVo getBarcodeTextile(String barcode, Long storeId);

	List<BarcodeTextileVo> getAllBarcodes(SearchFilterVo vo);

	String incrementQty(BarcodeTextileVo vo);

	String inventoryUpdateForTextile(List<UpdateInventoryRequest> request);

	List<AdjustmentsVo> getAllAdjustments(AdjustmentsVo vo);

	String saveProductTextileList(List<BarcodeTextileVo> barcodeTextileVos);

}
