package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QnaRepository extends JpaRepository<Qna, Long>, JpaSpecificationExecutor<Qna> {


  /*  Notice[] findByTypeWorkProduct(String Type);*/


}
