package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SizeRepository extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size> {
}
