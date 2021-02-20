package kr.co.kimpoziben.test.domain.repository;

import kr.co.kimpoziben.test.domain.entity.IjEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IjRepository extends JpaRepository<IjEntity, Long>, JpaSpecificationExecutor<IjEntity> {
    @Transactional
    @Modifying
    @Query(value=" UPDATE ij_board "
            + " SET del_yn = 'Y' "
            + " WHERE ij_seq = :seqAs ", nativeQuery = true)
    void updateYnDel(Long seqAs) throws Exception;
}
