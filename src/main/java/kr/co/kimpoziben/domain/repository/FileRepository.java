package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity>{
    //20201101 문익진 추가
    //다운로드 용 단일 파일 조회
    FileEntity findByIdFileAndSnFile(Long idFile, int snFile);

    //가장 번호가 높은 Entity 조회
    FileEntity findTopByIdFileOrderBySnFileDesc(Long idFile);

    //orgNmFile 조회
    FileEntity findTopBySrvNmFile(String srvNmFile);

    //20201101 문익진 추가
    //id의 파일 조회
    List<FileEntity> findByIdFileAndYnDel(Long idFile, String ynDelRp);

    void deleteAllByIdFileAndSnFile(Long idFile,int snFile) throws Exception;

    @Transactional
    @Modifying
    @Query(value=" UPDATE ij_file "
            + " SET del_yn = 'Y' "
            + " WHERE file_id = :idFile " +
            "and file_sn =:snFile ", nativeQuery = true)
    void updateYnDelFile(Long idFile, int snFile) throws Exception;


}
