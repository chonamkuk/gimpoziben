package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer>, JpaSpecificationExecutor<HistoryEntity>{

}
