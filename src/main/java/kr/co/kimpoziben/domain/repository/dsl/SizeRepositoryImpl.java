package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.QProdSize;
import kr.co.kimpoziben.domain.entity.QSize;
import kr.co.kimpoziben.domain.entity.Size;

import java.util.List;

public class SizeRepositoryImpl implements SizeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QSize size = QSize.size;
    private QProdSize prodSize = QProdSize.prodSize;

    public SizeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<Size> findBySeqProduct(Long seqProduct) {
        return queryFactory
                .selectFrom(size)
                .innerJoin(prodSize).on(size.seqSize.eq(prodSize.seqSize))
                .where(prodSize.seqProduct.eq(seqProduct))
                .orderBy(size.ordrSize.asc())
                .fetch();
    }

    @Override
    public long deleteSizeMappBySeqProduct(Long seqProduct) {
        return queryFactory.delete(prodSize)
                .where(prodSize.seqProduct.eq(seqProduct))
                .execute();
    }
}
