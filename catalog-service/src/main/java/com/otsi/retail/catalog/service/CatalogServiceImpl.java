/**
 * 
 */
package com.otsi.retail.catalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otsi.retail.catalog.common.Categories;
import com.otsi.retail.catalog.exceptions.DataNotFoundException;
import com.otsi.retail.catalog.exceptions.RecordNotFoundException;
import com.otsi.retail.catalog.mapper.CatalogMapper;
import com.otsi.retail.catalog.model.CatalogEntity;
import com.otsi.retail.catalog.repository.CatalogRepository;
import com.otsi.retail.catalog.vo.CatalogVo;

/**
 * @author Sudheer.Swamy
 *
 */
@Service
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogRepository catalogRepo;

	@Autowired
	private CatalogMapper catalogMapper;

	@Override
	public CatalogVo saveCatalogDetails(CatalogVo catalog) throws Exception {

		CatalogEntity entity = catalogMapper.convertVoToEntity(catalog);
		if (entity == null) {
			throw new DataNotFoundException("Data not found");
		}

		if (entity.getDescription() == Categories.Main_Category) {
			entity.setCUID(null);
		}
		/*
		 * else if (entity.getDescription() == Categories.Sub_Category) {
		 * //entity.setDescription(Categories.Main_Category);
		 * //entity.setCUID(Categories.Main_Category.getId());
		 * //entity.setName(Categories.Main_Category.getName());
		 * System.out.println("Sub-Category Details :- " + entity); } else if
		 * (entity.getDescription() == Categories.Leaf_Category) {
		 * entity.setDescription(Categories.Sub_Category);
		 * entity.setCUID(Categories.Sub_Category.getId());
		 * //entity.setName(Categories.Sub_Category.getName());
		 * System.out.println("Leaf-Category Details :- " + entity); }
		 */
		catalogRepo.save(entity);

		CatalogVo vo = catalogMapper.convertEntityToVo(entity);

		return vo;
	}

	@Override
	public CatalogVo getCatalogByName(String name) throws Exception {

		Optional<CatalogEntity> names = catalogRepo.findByName(name);

		if (!names.isPresent()) {
			throw new RecordNotFoundException("Given catalog name is not exists");
		} else {

			CatalogVo vo = catalogMapper.convertEntityToVo(names.get());
			return vo;
		}

	}

	@Override
	public List<CatalogVo> getAllCatalogs() {

		List<CatalogEntity> entity = catalogRepo.findAll();
		if (entity.isEmpty()) {
			throw new DataNotFoundException("Data not exists");
		}
		List<CatalogVo> vo = catalogMapper.convertEntityToVo(entity);
		return vo;
	}

	@Override
	public CatalogVo updateCatalog(Long id, CatalogVo vo) throws Exception {

		Optional<CatalogEntity> catalog = catalogRepo.findById(vo.getId());

		if (!catalog.isPresent()) {
			throw new RecordNotFoundException("Given catalog details are not exists");
		}
		if (catalog.get().getDescription() == Categories.Main_Category) {
			vo.setCUID(null);
		}

		CatalogEntity catalogSave = catalogMapper.convertVoToEntity(vo);
		catalogRepo.save(catalogSave);

		CatalogVo catalogSave1 = catalogMapper.convertEntityToVo(catalogSave);

		return catalogSave1;
	}

	@Override
	public void deleteCatalogById(Long id) throws Exception {

		Optional<CatalogEntity> entity = catalogRepo.findById(id);
		if (entity.isPresent()) {
			catalogRepo.deleteById(id);

		} else {
			throw new RecordNotFoundException("Given catalog details are not exists");
		}

	}

}
