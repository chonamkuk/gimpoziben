package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.ProdSize;
import kr.co.kimpoziben.domain.entity.QProdSize;
import kr.co.kimpoziben.domain.entity.QSize;

import java.util.List;

public class SizeRepositoryImpl implements SizeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QSize size = QSize.size;
    private QProdSize prodSize = QProdSize.prodSize;

    public SizeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<ProdSize> findBySeqProduct(Long seqProduct) {
        QueryResults<ProdSize> result = queryFactory
                .selectFrom(prodSize)
                .where(prodSize.seqProduct.eq(seqProduct))
                .fetchResults();

        return result.getResults();
    }
}
