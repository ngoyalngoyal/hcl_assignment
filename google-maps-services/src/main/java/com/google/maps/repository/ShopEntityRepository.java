/**
 * 
 */
package com.google.maps.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.google.maps.model.ShopEntity;

/**
 * @author NGoyal
 *
 */
@Repository
public interface ShopEntityRepository extends CrudRepository<ShopEntity, Long> {

	@Query("SELECT s FROM ShopEntity s WHERE LOWER(s.shopName) = LOWER(:shopName)")
	public ShopEntity findByShopName(@Param("shopName") String shopName);
	
	@Query("SELECT s FROM ShopEntity s WHERE s.latitide = :latitude and s.longitude = :longitude")
	public ShopEntity findByLatitudeLongitude(@Param("latitude") String latitude, @Param("longitude") String longitude);
}
