package kr.co.kimpoziben;

import kr.co.kimpoziben.domain.entity.ProdCate;
import kr.co.kimpoziben.domain.entity.ProdSize;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.OrderProductRepository;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import kr.co.kimpoziben.domain.repository.SizeRepository;
import kr.co.kimpoziben.dto.OrderProductDto;
import kr.co.kimpoziben.dto.SizeDto;
import kr.co.kimpoziben.service.SizeService;
import kr.co.kimpoziben.util.PageRequest;
import org.hibernate.collection.spi.PersistentCollection;
import org.junit.jupiter.api.Test;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@DataJpaTest
@Import(ApplicationConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest {

        @Autowired
        private OrderProductRepository orderProductRepository;

    @Test
        public void product_search() {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setSortProp("seqProduct");

            HashMap<String,Object> searchMap = new HashMap<String,Object> ();
            searchMap.put("seqOrder", 28L);
            searchMap.put("ynDisplay", "Y");
            List<OrderProductDto> productPage = orderProductRepository.findOrderProductByOrderId(searchMap);

            System.out.println(productPage);

            for(OrderProductDto orderProductDto : productPage) {
                System.out.println("orderProductDto :: " + orderProductDto);
            }
        }

}
