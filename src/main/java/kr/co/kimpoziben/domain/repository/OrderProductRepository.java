package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.OrderProduct;
import kr.co.kimpoziben.domain.repository.dsl.OrderProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, JpaSpecificationExecutor, OrderProductRepositoryCustom {
}
