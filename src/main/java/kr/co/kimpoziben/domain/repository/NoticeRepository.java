package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {




    @Query("SELECT e FROM Notice e WHERE e.dayNoticeStart <= CURRENT_DATE AND  CURRENT_DATE <= e.dayNoticeEnd ")
    List<Notice> findBydelYnAndYnNoticeMain(String delYn, String MainNoticeYn);

    @Query("SELECT e FROM Notice e WHERE e.ynDel= 'N' ")
    List<Notice> findTop4ByOrderByRegDt();

    @Query(nativeQuery = true, value = "select * from gps_notice  where DEL_YN= 'N' ORDER BY REGDT desc LIMIT 4")
    List<Notice> getSmallNoistList();



}
