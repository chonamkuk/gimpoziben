package kr.co.kimpoziben.domain.repository.dsl;

import kr.co.kimpoziben.domain.entity.Size;

import java.util.List;

public interface SizeRepositoryCustom {
    List<Size> findBySeqProduct(Long seqProduct);

    long deleteSizeMappBySeqProduct(Long seqProduct);
}
