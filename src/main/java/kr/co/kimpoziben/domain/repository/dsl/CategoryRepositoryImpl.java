package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.Category;
import kr.co.kimpoziben.domain.entity.QCategory;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QCategory category = QCategory.category;

    public CategoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
