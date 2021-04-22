package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.QProdCateMapp;
import kr.co.kimpoziben.domain.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QProduct product = QProduct.product;
    private QProdCateMapp prodCateMapp = QProdCateMapp.prodCateMapp;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Product> findBySeqCategory(HashMap<String,Object> searchMap, Pageable pageable) {
        QueryResults<Product> result = queryFactory
                .selectFrom(product)
                .where(
                        seqCategoryIn(searchMap),
                        ynDisplayEq(searchMap)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression seqCategoryIn(HashMap<String,Object> searchMap) {

        if(searchMap.getOrDefault("seqCategory",null) == null) {
            return null;
        }
        return product.seqProduct.in(JPAExpressions.select(prodCateMapp.product.seqProduct).from(prodCateMapp).where(prodCateMapp.category.seqCategory.eq((Long) searchMap.get("seqCategory"))));
    }

    private BooleanExpression ynDisplayEq(HashMap<String,Object> searchMap) {

        if(searchMap.getOrDefault("ynDisplay",null) == null) {
            return null;
        }

        return product.ynDisplay.eq((String) searchMap.get("ynDisplay"));
    }
}
