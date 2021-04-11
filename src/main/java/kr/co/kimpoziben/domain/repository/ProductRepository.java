package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.ProductEntity;
import kr.co.kimpoziben.test.domain.entity.AsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<AsEntity> {
}
