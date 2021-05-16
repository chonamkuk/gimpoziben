package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.dsl.SizeRepositoryCustom;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size>, SizeRepositoryCustom {
    List<Size> findAllByUpperSizeIsNull(Sort ordrSize);
}
