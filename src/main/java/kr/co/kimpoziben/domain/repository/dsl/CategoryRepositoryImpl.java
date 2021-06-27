package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.Category;
import kr.co.kimpoziben.domain.entity.QCategory;
import kr.co.kimpoziben.domain.entity.QProdCate;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QCategory category = QCategory.category;
    private QProdCate prodCate = QProdCate.prodCate;

    public CategoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Category> findBySeqProduct(Long seqProduct) {
        return queryFactory
                .selectFrom(category)
                .innerJoin(prodCate).on(category.seqCategory.eq(prodCate.seqCategory))
                .where(prodCate.seqProduct.eq(seqProduct))
                .orderBy(category.nmCategory.asc())
                .fetch();
    }

    @Override
    public long deleteCateMappBySeqProduct(Long seqProduct) {
        return queryFactory.delete(prodCate)
                .where(prodCate.seqProduct.eq(seqProduct))
                .execute();
    }
}
