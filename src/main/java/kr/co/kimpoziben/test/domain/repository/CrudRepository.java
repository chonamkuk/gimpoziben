package kr.co.kimpoziben.test.domain.repository;

import kr.co.kimpoziben.test.domain.entity.CrudEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRepository extends JpaRepository<CrudEntity, Long> {
}
