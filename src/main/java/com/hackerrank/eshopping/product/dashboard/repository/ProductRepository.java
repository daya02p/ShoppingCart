package com.hackerrank.eshopping.product.dashboard.repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "SELECT * FROM Product where category = :category order by availability desc, discounted_price asc, id asc", nativeQuery = true)
    List<Product> getProductByCategory(@Param("category") String category);

    @Query(value = "SELECT * FROM Product where category = :category and availability = :queryAvailable",nativeQuery = true)
    List<Product> getProductByCategoryAndAvailability(String category, boolean queryAvailable);

    @Query(value = "SELECT * FROM Product order by id asc",nativeQuery = true)
    List<Product> findAllProducts();
}
