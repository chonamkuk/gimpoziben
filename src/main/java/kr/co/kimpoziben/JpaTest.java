package kr.co.kimpoziben;

import kr.co.kimpoziben.domain.entity.ProdCateMapp;
import kr.co.kimpoziben.domain.entity.ProdSizeMapp;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import kr.co.kimpoziben.domain.repository.SizeRepository;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.test.domain.entity.AsEntity;
import kr.co.kimpoziben.util.PageRequest;
import kr.co.kimpoziben.util.SearchSpec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(ApplicationConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest {

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private SizeRepository sizeRepository;

        @Test
        public void product_search() {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setSortProp("seqProduct");

            HashMap<String,Object> searchMap = new HashMap<String,Object> ();
            searchMap.put("seqCategory", 6L);
            searchMap.put("ynDisplay", "Y");
            Page<Product> productPage = productRepository.findBySeqCategory(searchMap, pageRequest.of());

            System.out.println(productPage);
            System.out.println("getTotalElements :: " + productPage.getTotalElements());
            List<Product> productList = productPage.getContent();


            for(Product product : productList) {
                System.out.println("getNmVendor :: " + product.getVendor().getNmVendor());
                List<ProdCateMapp> prodCateMappList = product.getCateList();
                for(ProdCateMapp prodCateMapp : prodCateMappList) {
                    System.out.println("prodCateMapp :: " + prodCateMapp.getCategory().getNmCategory());
                }

                for(ProdSizeMapp prodSizeMapp : product.getSizeList()) {
                    System.out.println("prodSizeMapp :: " + prodSizeMapp.getSize().getNmSize());
                }
            }
        }

        @Test
        public void size_search() {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setSortProp("seqSize");

//            Page<Size> sizePage = sizeRepository.findAll(pageRequest.of());
//            List<Size> sizeList = sizePage.getContent();
            List<Size> sizeList = sizeRepository.findAll();

            for(Size size : sizeList) {
                System.out.println("size :: " + size.getNmSize());
            }
        }
}
