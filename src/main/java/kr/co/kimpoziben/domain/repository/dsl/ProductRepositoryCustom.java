package kr.co.kimpoziben.domain.repository.dsl;

import kr.co.kimpoziben.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface ProductRepositoryCustom {
    Page<Product> findBySeqCategory(HashMap<String,Object> searchMap, Pageable pageable);
}
