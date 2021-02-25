package com.hackerrank.eshopping.product.dashboard.controller;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.pojo.ProductDetailsRevised;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;
import com.hackerrank.eshopping.product.dashboard.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

    private ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product) {
        boolean status = productService.addProductIfNotExists(product);
        if (status) return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{product_id}")
    public ResponseEntity updateProductDetails(@PathVariable("product_id") Long productId, @RequestBody ProductDetailsRevised productDetailsRevised) {
        boolean status = productService.updateProductDetails(productId, productDetailsRevised);
        if (status) return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{product_id}")
    public ResponseEntity<String> getProductDetails(@PathVariable("product_id") Long productId) {
        Optional<Product> optionalProduct = productService.getProductWithId(productId);
        if (optionalProduct.isPresent()) return new ResponseEntity(JsonUtil.toJson(optionalProduct.get()), HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<String> getProductsByCategory(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "availability", required = false) Integer availability) throws UnsupportedEncodingException {
        List<Product> productList = null;

        if (category == null && availability == null) {
            return new ResponseEntity(JsonUtil.toJson(productService.getAllProducts()), HttpStatus.OK);
        }

        String decodedCategory = URLDecoder.decode(category, "UTF-8");
        if (availability == null)
            productList = productService.getProductsByCategory(decodedCategory);
        else
            productList = productService.getProductsByCategoryAndAvailability(decodedCategory, availability);
        return new ResponseEntity(JsonUtil.toJson(productList), HttpStatus.OK);
    }

}
