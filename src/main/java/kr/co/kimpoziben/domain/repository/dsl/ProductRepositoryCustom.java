package kr.co.kimpoziben.domain.repository.dsl;

import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> findBySeqCategory(HashMap<String,Object> searchMap, Pageable pageable);

//    List<ProductDto> findByOrderCnt(HashMap<String,Object> searchMap, Pageable pageable);
}
