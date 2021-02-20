package kr.co.kimpoziben.test.domain.repository;

import kr.co.kimpoziben.test.domain.entity.MeetEntity;
import kr.co.kimpoziben.test.domain.repository.dsl.MeetRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeetRepository extends JpaRepository<MeetEntity, Long>, JpaSpecificationExecutor<MeetEntity>, MeetRepositoryCustom {
}
