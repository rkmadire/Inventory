/**
 * 
 */
package com.otsi.retail.inventory.service;

import java.util.List;
import com.otsi.retail.inventory.vo.CatalogVo;

/**
 * @author Sudheer.Swamy
 *
 */
public interface CatalogService {

	public CatalogVo saveCatalogDetails(CatalogVo catalog) throws Exception;

	public CatalogVo getCatalogByName(String name) throws Exception;

	//public List<CatalogVo> getAllCatalogs();

	//public CatalogVo updateCatalog(Long id, CatalogVo vo) throws Exception;

	public void deleteCategoryById(Long id) throws Exception;

	public List<CatalogVo> getCategories(Long id);

	public List<CatalogVo> getMainCategories();

	public List<CatalogVo> getAllCategories();
	
	

}
