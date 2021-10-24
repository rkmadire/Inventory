package com.otsi.retail.inventory.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.otsi.retail.inventory.model.ProductTextile;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;
import com.otsi.retail.inventory.vo.ProductTextileVo;

@Service
public interface ProductTextileService {

	String saveProductTextile(ProductTextileVo textileVo);

	ProductTextileVo getProductTextile(Long productTextileId);

}
