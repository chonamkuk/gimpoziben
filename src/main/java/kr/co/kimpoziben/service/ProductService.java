package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.*;
import kr.co.kimpoziben.domain.repository.*;
import kr.co.kimpoziben.dto.*;
import lombok.AllArgsConstructor;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
//@org.springframework.transaction.annotation.Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProdSizeRepository prodSizeRepository;
    @Autowired
    private ProdCateRepository prodCateRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private AttachService attachService;

    ModelMapper modelMapper = null;

    private ProductService() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setPropertyCondition(new Condition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> mappingContext) {
                return !(mappingContext.getSource() instanceof PersistentCollection);
            }
        });
        modelMapper.typeMap(ProductDto.class, Product.class);
    }

    public HashMap getList(Pageable pageble, HashMap<String,Object> searchMap) throws Exception {
        HashMap result = new HashMap();
        Page<Product> productPage = productRepository.findBySeqCategory(searchMap, pageble);
        List<ProductDto> productList = new ArrayList<>();
        if(productPage.getContent().size() > 0) {
            for(Product product : productPage.getContent()) {
                ProductDto productDto = new ProductDto();
                productDto.setSeqProduct(product.getSeqProduct());
                productDto.setNmProduct(product.getNmProduct());
                productDto.setTitleProduct(product.getTitleProduct());
                productDto.setSellPrice(product.getSellPrice());
                productDto.setYnSoldOut(product.getYnSoldOut());
                productDto.setYnDisplay(product.getYnDisplay());
                productDto.setIdMainImg(product.getIdMainImg());
                productDto.setDescProduct(product.getDescProduct());

                productList.add(productDto);
            }
        }

        result.put("pagingResult", productPage);
        result.put("resultList", productList);
        return result;
    }

    @Transactional
    public Long save(ProductDto productDto) {
        if(productDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(productDto.getImageList(), "product");
            if (attachEntities != null) {
                productDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }

        /** todo: 비어있는 값들이 null로 입력되는 경우 수정해야 함
         *         into
         *             gps_product
         *             (price_buy, product_color, product_desc, main_img_id, moddt, modifier, product_nm, regdt, register, price_sell, product_title, vendor_seq, display_yn, soldout_yn)
         *         values
         *             (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
         */

        Product newProduct = productRepository.save(modelMapper.map(productDto, Product.class));

        for(ProdCateDto prodCateDto : productDto.getCateList()) {
            ProdCate prodCate = ProdCate.builder()
                    .seqCategory(prodCateDto.getSeqCategory())
                    .seqProduct(newProduct.getSeqProduct())
                    .build();
            prodCateRepository.save(prodCate);
        }

        for(ProdSizeDto prodSizeDto : productDto.getSizeList()) {
            if(prodSizeDto.getSeqSize() != null) {
                prodSizeDto.setSeqProduct(newProduct.getSeqProduct());
                prodSizeRepository.save(modelMapper.map(prodSizeDto, ProdSize.class));
            }
        }

        return newProduct.getSeqProduct();
    }

    @Transactional
    public Long update(ProductDto productDto) {
        if(productDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(productDto.getImageList(), "product", productDto.getIdMainImg());
            if (attachEntities != null) {
                productDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }

        Product newProduct = productRepository.save(modelMapper.map(productDto, Product.class));

        categoryRepository.deleteCateMappBySeqProduct(productDto.getSeqProduct());
        for(ProdCateDto prodCateDto : productDto.getCateList()) {
            ProdCate prodCate = ProdCate.builder()
                    .seqCategory(prodCateDto.getSeqCategory())
                    .seqProduct(newProduct.getSeqProduct())
                    .build();
            prodCateRepository.save(prodCate);
        }

        sizeRepository.deleteSizeMappBySeqProduct(productDto.getSeqProduct());
        for(ProdSizeDto prodSizeDto : productDto.getSizeList()) {
            if(prodSizeDto.getSeqSize() != null) {
                prodSizeDto.setSeqProduct(newProduct.getSeqProduct());
                prodSizeRepository.save(modelMapper.map(prodSizeDto, ProdSize.class));
            }
        }

        return newProduct.getSeqProduct();
    }

    public ProductDto getProductDetail(Long seqProduct) throws  Exception {
        Product product = productRepository.findById(seqProduct).orElse(null);
        if(product != null) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            return productDto;
        }

        return null;
    }
}
