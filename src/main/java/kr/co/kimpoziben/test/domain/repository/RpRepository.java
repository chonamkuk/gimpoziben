package kr.co.kimpoziben.test.domain.repository;

import kr.co.kimpoziben.test.domain.entity.RpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RpRepository extends JpaRepository<RpEntity, Long>, JpaSpecificationExecutor<RpEntity>{
    List<RpEntity> findByIdRpOrderBySeqRpDesc(Long idRp);

    List<RpEntity> findByIdRpAndYnDelRpOrderBySeqRpDesc(Long idRp, String ynDelRp);

    @Transactional
    @Modifying
    @Query(value=" UPDATE ij_reply "
            + " SET rp_del_yn = 'Y' "
            + " WHERE rp_seq = :seqRp ", nativeQuery = true)
    void updateYnDelRp(Long seqRp) throws Exception;

    @Transactional
    @Modifying
    @Query(value=" UPDATE ij_reply "
            + " SET rp_comment = :commentRp "
            + " WHERE rp_seq = :seqRp ", nativeQuery = true)
    void updateCommentRp(Long seqRp, String commentRp) throws Exception;


}
