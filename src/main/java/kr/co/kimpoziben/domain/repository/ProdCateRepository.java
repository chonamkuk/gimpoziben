package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.ProdCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProdCateRepository extends JpaRepository<ProdCate, Long>, JpaSpecificationExecutor<ProdCate> {
}
