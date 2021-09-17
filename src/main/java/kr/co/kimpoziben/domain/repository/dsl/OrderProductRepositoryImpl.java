package kr.co.kimpoziben.domain.repository.dsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.domain.entity.*;
import kr.co.kimpoziben.dto.OrderDto;
import kr.co.kimpoziben.dto.OrderProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderProductRepositoryImpl implements OrderProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QOrderProduct orderProduct = QOrderProduct.orderProduct;
    private QProduct product = QProduct.product;
    private QSize size = QSize.size;

    public OrderProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<OrderProductDto> findOrderProductByOrderId(HashMap<String, Object> searchMap) {
        QueryResults<OrderProductDto> result = queryFactory
                .select(
                        Projections.fields(OrderProductDto.class,
                            orderProduct.seqProductOrder,
                            orderProduct.seqOrder,
                            orderProduct.cartId,
                            orderProduct.seqProduct,
                            ExpressionUtils.as(JPAExpressions.select(product.nmProduct).from(product).where(product.seqProduct.eq(orderProduct.seqProduct)),"nmProduct"),
                            orderProduct.seqSize,
                            ExpressionUtils.as(JPAExpressions.select(size.nmSize).from(size).where(size.seqSize.eq(orderProduct.seqSize)), "nmSize"),
                            orderProduct.idMainImg,
                            orderProduct.amountOrder,
                            orderProduct.priceOrder
                        )
                )
                .from(orderProduct)
                .where(
                        orderProduct.seqOrder.eq(Long.valueOf(searchMap.get("seqOrder").toString()))
                )
                .fetchResults();

        return result.getResults();
    }

    @Override
    public Page<OrderProductDto> findOrderProductByCustomerId(HashMap<String, Object> searchMap, Pageable pageable) {
        QueryResults<OrderProductDto> result = queryFactory
                .select(
                        Projections.fields(OrderProductDto.class,
                                orderProduct.seqProductOrder,
                                orderProduct.seqOrder,
                                orderProduct.cartId,
                                orderProduct.seqProduct,
                                ExpressionUtils.as(JPAExpressions.select(product.nmProduct).from(product).where(product.seqProduct.eq(orderProduct.seqProduct)),"nmProduct"),
                                orderProduct.seqSize,
                                ExpressionUtils.as(JPAExpressions.select(size.nmSize).from(size).where(size.seqSize.eq(orderProduct.seqSize)), "nmSize"),
                                orderProduct.idMainImg,
                                orderProduct.amountOrder,
                                orderProduct.priceOrder
                        )
                )
                .from(orderProduct)
                .where(
                        orderProduct.seqOrder.eq(Long.valueOf(searchMap.get("seqOrder").toString()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
