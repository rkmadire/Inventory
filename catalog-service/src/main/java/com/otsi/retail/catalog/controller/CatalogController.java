/**
 * 
 */
package com.otsi.retail.catalog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.catalog.common.Categories;
import com.otsi.retail.catalog.gateway.GateWayResponse;
import com.otsi.retail.catalog.model.CatalogEntity;
import com.otsi.retail.catalog.service.CatalogService;
import com.otsi.retail.catalog.vo.CatalogVo;

/**
 * @author Sudheer.Swamy
 *
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	private final Logger LOGGER = LoggerFactory.getLogger(CatalogController.class);

	@PostMapping("/saveCatalog")
	public GateWayResponse<?> saveCatalog(@RequestBody CatalogVo catalog) throws Exception {

		CatalogVo result = catalogService.saveCatalogDetails(catalog);
		LOGGER.info("Received request to getListOfReturnSlips:" + result);
		return new GateWayResponse<>(result);

	}

	@GetMapping("/getCatalogByName/{name}")
	public GateWayResponse<?> getCatalogbyName(@PathVariable("name") String name) throws Exception {
		CatalogVo vo = catalogService.getCatalogByName(name);
		LOGGER.info("Received request to getCatalogByName:" + vo);
		return new GateWayResponse<>(vo);
	}

	@GetMapping("/ListOfCatalogs")
	public GateWayResponse<?> getListOfCatalogs() {

		List<CatalogVo> vo = catalogService.getAllCatalogs();
		LOGGER.info("Received request to getListOfCatalogs:" + vo);
		return new GateWayResponse<>(vo);

	}

	@PutMapping("/updateCatalog/{id}")
	public GateWayResponse<?> updateCatalog(@PathVariable Long id, @RequestBody CatalogVo vo) throws Exception {

		CatalogVo catalogVo = catalogService.updateCatalog(id, vo);
		LOGGER.info("Received request to updateCatalog:" + catalogVo);
		return new GateWayResponse<>(catalogVo);

	}

	@DeleteMapping("/deleteCatalog/{id}")
	public GateWayResponse<?> deleteCatalog(@PathVariable("id") Long id) throws Exception {

		catalogService.deleteCatalogById(id);
		LOGGER.info("Received request to  deleteCatalog");
		return new GateWayResponse<>("Catalog deleted successfully");

	}
	

}
