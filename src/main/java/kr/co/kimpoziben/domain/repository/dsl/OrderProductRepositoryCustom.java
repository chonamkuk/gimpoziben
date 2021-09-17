package kr.co.kimpoziben.domain.repository.dsl;

import kr.co.kimpoziben.domain.entity.OrderProduct;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.dto.OrderDto;
import kr.co.kimpoziben.dto.OrderProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface OrderProductRepositoryCustom {
    List<OrderProductDto> findOrderProductByOrderId(HashMap<String, Object> searchMap);

    public Page<OrderProductDto> findOrderProductByCustomerId(HashMap<String, Object> searchMap, Pageable pageable);
}
