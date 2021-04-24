package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.repository.dsl.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
