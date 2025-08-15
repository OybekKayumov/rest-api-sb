package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @EntityGraph(attributePaths = "category")
  List<Product> findByCategoryId(Byte categoryId);

  //* improve sql queries
  @EntityGraph(attributePaths = "category")
  @Query("select p from Product")
  //@Query("select p from Product p JOIN FETCH p.category")
  List<Product> findAllWithCategory();
}