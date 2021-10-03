package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.code.OrderStat;
import kr.co.kimpoziben.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAllByIdCustomerAndStatOrderNotOrderBySeqOrderDesc(Long idCustomer, OrderStat orderStat);
}
