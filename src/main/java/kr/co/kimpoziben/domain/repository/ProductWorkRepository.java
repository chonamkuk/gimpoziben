package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.ProductWork;
import kr.co.kimpoziben.domain.repository.dsl.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductWorkRepository extends JpaRepository<ProductWork, Long>, JpaSpecificationExecutor<ProductWork> {
}
