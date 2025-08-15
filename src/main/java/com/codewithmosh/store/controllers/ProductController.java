package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

//  public ProductController(ProductRepository productRepository) {
//    this.productRepository = productRepository;
//  }

  @GetMapping
  public List<ProductDto> getAllProducts() {

    return productRepository.findAll().stream()
            .map(productMapper::toDto)
            .toList();
  }
}
