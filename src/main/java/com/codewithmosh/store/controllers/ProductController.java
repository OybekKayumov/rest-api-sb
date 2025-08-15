package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;

//  public ProductController(ProductRepository productRepository) {
//    this.productRepository = productRepository;
//  }

  public List<ProductDto> getAllProducts() {

    productRepository.findAll();
  }
}
