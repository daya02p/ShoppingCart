package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.pojo.ProductDetailsRevised;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean addProductIfNotExists(Product product) {
        if (null == product) throw new IllegalArgumentException("Received empty product to add.");

        if (checkIfProductAlreadyExists(product.getId()).isPresent()) {
            return false;
        }
        productRepository.save(product);
        return true;
    }

    public boolean updateProductDetails(Long productId, ProductDetailsRevised productDetailsRevised) {

        Optional<Product> optionalProduct = checkIfProductAlreadyExists(productId);
        if (!optionalProduct.isPresent()) return false;

        Product product = optionalProduct.get();
        product.setRetail_price(productDetailsRevised.getRetail_price());
        product.setDiscounted_price(productDetailsRevised.getDiscounted_price());
        product.setAvailability(productDetailsRevised.isAvailability());
        productRepository.save(product);
        return true;
    }

    private Optional<Product> checkIfProductAlreadyExists(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductWithId(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductsByCategory(String category) {

        List<Product> productList = productRepository.getProductByCategory(category);
        if (productList == null || productList.size() == 0) {
            return Collections.emptyList();
        }
        return productList;
    }

    public List<Product> getProductsByCategoryAndAvailability(String category, Integer availability) {
        boolean queryAvailable = true;
        if (availability == 0) queryAvailable = false;
        List<Product> productList = productRepository.getProductByCategoryAndAvailability(category, queryAvailable);
        Collections.sort(productList, new ComparatorList());
        System.out.println(productList);
        return productList;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    private class ComparatorList implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            int discountPercentage1 = (int)Math.ceil(((o1.getRetail_price() - o1.getDiscounted_price())/o1.getRetail_price())*100);
            int discountPercentage2 = (int)Math.ceil(((o2.getRetail_price() - o2.getDiscounted_price())/o2.getRetail_price())*100);

            if (discountPercentage1 < discountPercentage2) {
                return 1;
            } else if(discountPercentage1 > discountPercentage2) {
                return -1;
            }

            if (o1.getDiscounted_price() < o2.getDiscounted_price()) {
                return -1;
            } else if (o1.getDiscounted_price() > o2.getDiscounted_price()) {
                return 1;
            }
            return o1.getId() > o2.getId() ? 1 : -1;
        }

    }
}
