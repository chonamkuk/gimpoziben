package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.ProdSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProdSizeRepository extends JpaRepository<ProdSize, Long>, JpaSpecificationExecutor<ProdSize> {
}
