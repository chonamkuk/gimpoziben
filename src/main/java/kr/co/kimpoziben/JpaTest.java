package kr.co.kimpoziben;

import kr.co.kimpoziben.domain.entity.ProdCate;
import kr.co.kimpoziben.domain.entity.ProdSize;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import kr.co.kimpoziben.domain.repository.SizeRepository;
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
        private ProductRepository productRepository;

        @Autowired
        private SizeRepository sizeRepository;


        private SizeService sizeService;



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
                List<ProdCate> prodCateList = product.getCateList();
                for(ProdCate prodCate : prodCateList) {
//                    System.out.println("prodCateMapp :: " + prodCate.getCategory().getNmCategory());
                }

                for(ProdSize prodSize : product.getSizeList()) {
//                    System.out.println("prodSizeMapp :: " + prodSize.getSize().getNmSize());
                }
            }
        }

        @Test
        public void size_search() throws Exception {
            List<SizeDto> sizeList = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setPropertyCondition(new Condition<Object, Object>() {
                @Override
                public boolean applies(MappingContext<Object, Object> mappingContext) {
                    return !(mappingContext.getSource() instanceof PersistentCollection);
                }
            });

            for(Size size : sizeRepository.findAll(Sort.by(Sort.Direction.ASC, "ordrSize"))) {
//                SizeDto sizeDto = new SizeDto();
//                sizeDto.setSeqSize(size.getSeqSize());
//                sizeDto.setNmSize(size.getNmSize());
//            if(size.getUpperSize() != null) {
//                sizeDto.setUpperSize(size.getUpperSize());
//            }
//                sizeList.add(sizeDto);

                sizeList.add(modelMapper.map(size, SizeDto.class));
            }

            for(SizeDto sizeDto : sizeList) {
                if(sizeDto.getUpperSize() != null) {
                    System.out.println(sizeDto.getNmSize() + " => " + sizeDto.getUpperSize().getNmSize());
                    sizeDto.getUpperSize().addSubSize(sizeDto);
                    System.out.println(sizeDto.getUpperSize().getSubSizeList().get(0).getNmSize());
                }
            }

            for(SizeDto sizeDto : sizeList) {
                if(sizeDto.getSubSizeList() != null) {
                    System.out.println(sizeDto.getNmSize());
                    for(SizeDto sizeDto1 : sizeDto.getSubSizeList()) {
                        System.out.println(sizeDto1.getNmSize() + " => " + sizeDto1.getUpperSize().getNmSize());
                    }
                }
            }
        }
}
