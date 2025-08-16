package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

//  public ProductController(ProductRepository productRepository, ProductMapper productMapper) {
//    this.productRepository = productRepository;
//	  this.productMapper = productMapper;
//  }

  @GetMapping
  public List<ProductDto> getAllProducts(
      @RequestParam(name = "categoryId", required = false) Byte categoryId
  ) {

    List<Product> products;

    if (categoryId != null) {
      products = productRepository.findByCategoryId(categoryId);
    } else {
      //products = productRepository.findAll();
      //* improve sql queries
      products = productRepository.findAllWithCategory();
    }
//    return productRepository.findAll().stream()
//            .map(productMapper::toDto)
//            .toList();

    return products.stream()
            .map(productMapper::toDto)
            .toList();
  }

	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {

		var product = productMapper.toEntity(productDto);
		productRepository.save(product);
		productDto.setId(product.getId());

		return ResponseEntity.ok(productDto);

	}
}
