package kr.co.kimpoziben;

import kr.co.kimpoziben.domain.entity.ProdCateMapp;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(JpaTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest {

        @Autowired
        private ProductRepository productRepository;

        @Test
        public  void search() {
            System.out.println(productRepository);

            List<Product> productList = productRepository.findAll();

            System.out.println(productList);

            for(Product product : productList) {
                System.out.println("getNmVendor :: " + product.getVendor().getNmVendor());
                List<ProdCateMapp> prodCateMappList = product.getMappList();
                for(ProdCateMapp prodCateMapp : prodCateMappList) {
                    System.out.println("prodCateMapp :: " + prodCateMapp.getCategory().getNmCategory());
                }
            }
        }
}
