/**
 * 
 */
package com.otsi.retail.inventory.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.service.CatalogService;
import com.otsi.retail.inventory.vo.CatalogVo;
import com.otsi.retail.inventory.vo.UomVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sudheer.Swamy
 *
 */
@Api(value = "CatalogController", description = "REST APIs related to CatalogEntity !!!!")
@RestController
@RequestMapping("/catalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	private final Logger LOGGER = LogManager.getLogger(CatalogController.class);

	@ApiOperation(value = "saveCatalog", notes = "saving catlog", response = CatalogVo.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 200, message = "Successful retrieval", 
			response = CatalogVo.class, responseContainer = "Object") })
	@PostMapping("/saveCatalog")
	public GateWayResponse<?> saveCatalog(@RequestBody CatalogVo catalog) throws Exception {

		CatalogVo result = catalogService.saveCatalogDetails(catalog);
		LOGGER.info("Received request to savecatalog:" + result);
		return new GateWayResponse<>(result);

	}

	@ApiOperation(value = "getCatalogByName", notes = "fetching catalog using name", response = CatalogVo.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 200, message = "Successful retrieval", 
			response = UomVo.class, responseContainer = "Object") })
	@GetMapping("/getCatalogByName/{name}")
	public GateWayResponse<?> getCatalogbyName(@PathVariable("name") String name) throws Exception {
		CatalogVo vo = catalogService.getCatalogByName(name);
		LOGGER.info("Received request to getCatalogByName:" + vo);
		return new GateWayResponse<>(vo);
	}

	@ApiOperation(value = "ListOfDivisions", notes = "fetching list of divisions", response = CatalogVo.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 200, message = "Successful retrieval", 
			response = CatalogVo.class, responseContainer = "List") })
	@GetMapping("/ListOfDivisions")
	public GateWayResponse<?> getListOfMainCatagories() {

		List<CatalogVo> vo = catalogService.getMainCategories();
		LOGGER.info("Received request to getListOfCategories:" + vo);
		return new GateWayResponse<>(vo);

	}

	@ApiOperation(value = "getcategoriesByid", notes = "fetching categories using id", response = CatalogVo.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 200, message = "Successful retrieval", 
			response = CatalogVo.class, responseContainer = "List") })
	@GetMapping("/getcategoriesByid")
	public GateWayResponse<?> getCategories(@RequestParam Long id) {
		List<CatalogVo> vo = catalogService.getCategories(id);
		LOGGER.info("Received request to getcategories:" + vo);

		return new GateWayResponse<>(vo);

	}

	@ApiOperation(value = "deleteCategory", notes = "delete category using id", response = CatalogVo.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 200, message = "Successful retrieval", 
			response = CatalogVo.class, responseContainer = "String") })
	@DeleteMapping("/deleteCategory/{id}")
	public GateWayResponse<?> deleteCategory(@PathVariable("id") Long id) throws Exception {

		catalogService.deleteCategoryById(id);
		LOGGER.info("Received request to  deleteCatalog");
		return new GateWayResponse<>("Catalog deleted successfully");

	}
	
	@ApiOperation(value = "getListOfCategories", notes = "fetching list of categories", response = CatalogVo.class)
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Server error"),
			@ApiResponse(code = 200, message = "Successful retrieval", 
			response = CatalogVo.class, responseContainer = "List") })
	@GetMapping("/ListOfAllCategories")
	public GateWayResponse<?> getListOfCategories() {

		List<CatalogVo> vo = catalogService.getAllCategories();
		LOGGER.info("Received request to getAllListOfCategories:" + vo);
		return new GateWayResponse<>(vo);

	}

}
