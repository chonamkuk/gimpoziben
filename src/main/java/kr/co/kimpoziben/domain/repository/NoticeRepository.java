package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {


  /*  Notice[] findByTypeWorkProduct(String Type);*/


}
