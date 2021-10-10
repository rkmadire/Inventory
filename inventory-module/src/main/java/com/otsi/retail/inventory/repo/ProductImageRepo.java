/**
 * 
 */
package com.otsi.retail.inventory.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.ProductImage;

/**
 * @author vasavi
 *
 */
@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {

	//List<ProductImage> save(List<ProductImage> listImages);

}
