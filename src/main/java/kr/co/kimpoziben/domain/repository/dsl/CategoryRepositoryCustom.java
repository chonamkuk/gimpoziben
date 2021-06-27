package kr.co.kimpoziben.domain.repository.dsl;

import kr.co.kimpoziben.domain.entity.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findBySeqProduct(Long seqProduct);

    long deleteCateMappBySeqProduct(Long seqProduct);
}
