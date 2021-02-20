package kr.co.kimpoziben.test.domain.repository;

import kr.co.kimpoziben.test.domain.entity.AsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AsRepository extends JpaRepository<AsEntity, Long>, JpaSpecificationExecutor<AsEntity> {
    @Transactional
    @Modifying
    @Query(value=" UPDATE as_board "
            + " SET del_yn = 'Y' "
            + " WHERE as_seq = :seqAs ", nativeQuery = true)
    void updateYnDel(Long seqAs) throws Exception;
}
