package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.ProdCateMapp;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.repository.dsl.ProductRepositoryCustom;
import kr.co.kimpoziben.util.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {
    Page<Product> findByYnDisplayEquals(String ynDisplay, Pageable pageable);
}
