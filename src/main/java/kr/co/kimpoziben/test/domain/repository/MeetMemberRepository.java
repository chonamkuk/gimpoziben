package kr.co.kimpoziben.test.domain.repository;

import kr.co.kimpoziben.test.domain.entity.MeetMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeetMemberRepository extends JpaRepository<MeetMemberEntity, Long>, JpaSpecificationExecutor<MeetMemberEntity> {
}
