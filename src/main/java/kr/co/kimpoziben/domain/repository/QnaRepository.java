package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Notice;
import kr.co.kimpoziben.domain.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long>, JpaSpecificationExecutor<Qna> {


  /*  Notice[] findByTypeWorkProduct(String Type);*/



    @Query(nativeQuery = true, value = "select * from gps_qna  where DEL_YN= 'N' ORDER BY REGDT desc LIMIT 4")
    List<Qna> getSmallQnaList();


}
