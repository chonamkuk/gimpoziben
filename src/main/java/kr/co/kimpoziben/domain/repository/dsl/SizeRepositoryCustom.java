package kr.co.kimpoziben.domain.repository.dsl;

import kr.co.kimpoziben.domain.entity.ProdSize;

import java.util.List;

public interface SizeRepositoryCustom {
    List<ProdSize> findBySeqProduct(Long seqProduct);
}
