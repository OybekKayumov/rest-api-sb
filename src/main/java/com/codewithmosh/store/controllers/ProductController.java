package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
	private final CategoryRepository categoryRepository;

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
	public ResponseEntity<ProductDto> createProduct(
					@RequestBody ProductDto productDto,
					UriComponentsBuilder uriBuilder) {

		var category =
						categoryRepository.findById(productDto.getCategoryId()).orElse(null);
		if (category == null) {
			return ResponseEntity.badRequest().build();
		}

		var product = productMapper.toEntity(productDto);
		product.setCategory(category);
		productRepository.save(product);
		productDto.setId(product.getId());

		var uri =
						uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();

		//return ResponseEntity.ok(productDto);
		return ResponseEntity.created(uri).body(productDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(
					@PathVariable Long id,
					@RequestBody ProductDto productDto) {

		var product = productRepository.findById(id).orElse(null);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}

		productMapper.update(productDto, product);
		productRepository.save(product);

		return ResponseEntity.ok(productDto);
	}
}
