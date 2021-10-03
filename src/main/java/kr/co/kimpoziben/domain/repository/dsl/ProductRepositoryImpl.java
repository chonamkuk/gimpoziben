package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.*;
import kr.co.kimpoziben.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QProduct product = QProduct.product;
    private QProdCate prodCate = QProdCate.prodCate;
    private QCategory category = QCategory.category;
    private QVendor vendor = QVendor.vendor;
    private QOrderProduct orderProduct = QOrderProduct.orderProduct;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Product> findBySeqCategory(HashMap<String,Object> searchMap, Pageable pageable) {
        QueryResults<Product> result = queryFactory
                .selectFrom(product)
                .where(
                        seqCategoryIn(searchMap),
                        seqUpperIn(searchMap),
                        ynDisplayEq(searchMap)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

//    @Override
//    public List<ProductDto> findByOrderCnt(HashMap<String,Object> searchMap, Pageable pageable) {
//        QueryResults<ProductDto> result = queryFactory
//                .select(
//                        Projections.fields(ProductDto.class,
//                                product.seqProduct,
//                                product.titleProduct,
//                                product.nmProduct,
//                                product.descProduct,
//                                product.colorProduct,
//                                product.buyPrice,
//                                product.sellPrice,
//                                product.ynDisplay,
//                                product.ynSoldOut,
//                                product.idMainImg
//                        )
//                )
//                .from(product)
//                .join(
//
//                )
//                .where(
//                        ynDisplayEq(searchMap)
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
//                .fetchResults();
//
//
//        return result.getResults();
//    }

    private BooleanExpression seqCategoryIn(HashMap<String,Object> searchMap) {
        if(searchMap.getOrDefault("seqCategory",null) == null) {
            return null;
        }
        return product.seqProduct.in(JPAExpressions.select(prodCate.seqProduct).from(prodCate).where(prodCate.seqCategory.eq((Long) searchMap.get("seqCategory"))));
    }

    private BooleanExpression seqUpperIn(HashMap<String,Object> searchMap) {
        if(searchMap.getOrDefault("seqUpper",null) == null) {
            return null;
        }
        return product.seqProduct.in(
                JPAExpressions
                        .select(prodCate.seqProduct)
                        .from(prodCate)
                        .where(
                                prodCate.seqCategory.in(JPAExpressions
                                        .select(category.seqCategory)
                                        .from(category)
                                        .where(
                                                category.seqUpper.eq((Long) searchMap.get("seqUpper"))
                                        )
                                )
                        )
        );
    }

    private BooleanExpression ynDisplayEq(HashMap<String,Object> searchMap) {
        if(searchMap.getOrDefault("ynDisplay",null) == null) {
            return null;
        }
        return product.ynDisplay.eq((String) searchMap.get("ynDisplay"));
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        // Sort
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Product.class, "product");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        return orders;
    }
}
