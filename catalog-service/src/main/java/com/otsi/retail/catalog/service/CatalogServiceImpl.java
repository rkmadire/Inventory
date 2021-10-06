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
import com.otsi.retail.catalog.exceptions.DuplicateRecordException;
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
		CatalogEntity entity = new CatalogEntity();

		entity = catalogMapper.convertVoToEntity(catalog);
		if (entity == null) {
			throw new DataNotFoundException("Data not found");
		}

		if (catalog.getCUID() == 0) {
			entity.setParent(null);
		} else {
			CatalogEntity centity = this.catalogRepo.getById(catalog.getCUID());
			entity.setParent(centity);
		}

		catalogRepo.save(entity);

		CatalogVo vo = catalogMapper.convertEntityToVo(entity);
		if (entity.getParent() != null) {
			vo.setCUID(entity.getParent().getId());
		} else {
			vo.setCUID(null);

		}
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
	public void deleteCategoryById(Long id) throws Exception {

		if (catalogRepo.findById(id).isPresent()) {
			if (catalogRepo.findByParentId(id).isEmpty()) {
				catalogRepo.deleteById(id);
			} else {
				throw new DuplicateRecordException(
						"Failed to delete,  Please delete child categories associated with this category");
			}

		} else

			throw new RecordNotFoundException("record Not found");
	}

	@Override
	public List<CatalogVo> getCategories(Long id) {

		Optional<CatalogEntity> entity = catalogRepo.findById(id);
		if (entity.isEmpty()) {
			throw new RecordNotFoundException("record not exists");
		}

		List<CatalogEntity> pentity = catalogRepo.findByParentId(entity.get().getId());
		if (pentity.isEmpty()) {
			throw new RecordNotFoundException("record not exists");
		}
		
		List<CatalogVo> lvo = catalogMapper.convertlistEntityToVo(pentity);
		
		return lvo;
	}

	@Override
	public List<CatalogVo> getMainCategories() {
		
		List<CatalogEntity> ent = catalogRepo.findByDescription(Categories.Main_Category);
		if (ent.isEmpty()) {
			throw new RecordNotFoundException("record not exists");
		}
		
		List<CatalogVo> lvo = catalogMapper.convertlEntityToVo(ent);
		return lvo;
	}

}
