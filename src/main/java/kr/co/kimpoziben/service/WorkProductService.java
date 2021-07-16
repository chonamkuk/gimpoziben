package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.ProductWork;
import kr.co.kimpoziben.domain.repository.ProdCateRepository;
import kr.co.kimpoziben.domain.repository.ProdSizeRepository;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import kr.co.kimpoziben.domain.repository.ProductWorkRepository;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.ProductWorkDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
//@org.springframework.transaction.annotation.Transactional
public class WorkProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductWorkRepository productWorkRepository;
    @Autowired
    private ProdSizeRepository prodSizeRepository;
    @Autowired
    private ProdCateRepository prodCateRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private SizeService sizeService;

    ModelMapper modelMapper = null;

    private WorkProductService() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setPropertyCondition(new Condition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> mappingContext) {
                return !(mappingContext.getSource() instanceof PersistentCollection);
            }
        });
        modelMapper.typeMap(ProductDto.class, Product.class)
//                .addMapping(ProductDto::getCateList, Product::setCateList)
                .addMapping(ProductDto::getSizeList, Product::setSizeList)
        ;
//        modelMapper.typeMap(CategoryDto.class, Category.class);
//        modelMapper.typeMap(VendorDto.class, Vendor.class);
//        modelMapper.typeMap(SizeDto.class, Size.class);
//        modelMapper.typeMap(ProdCateDto.class, ProdCate.class);
//        modelMapper.typeMap(ProdSizeDto.class, ProdSize.class);
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
    public Long save(ProductWorkDto productWorkDto) {
        if(productWorkDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(productWorkDto.getImageList(), "product");
            if (attachEntities != null) {
                productWorkDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }

        /** todo: 비어있는 값들이 null로 입력되는 경우 수정해야 함
         *         into
         *             gps_product
         *             (price_buy, product_color, product_desc, main_img_id, moddt, modifier, product_nm, regdt, register, price_sell, product_title, vendor_seq, display_yn, soldout_yn)
         *         values
         *             (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
         */
        productWorkDto.setRegDt(LocalDateTime.now());
        productWorkDto.setTypeWorkProduct("J");
        ProductWork newProduct = productWorkRepository.save(modelMapper.map(productWorkDto, ProductWork.class));
  /*      for(ProdCateDto prodCateDto : productDto.getCateList()) {
            ProdCate prodCate = ProdCate.builder()
                    .seqCategory(prodCateDto.getCategory().getSeqCategory())
                    .seqProduct(newProduct.getSeqProduct())
                    .build();
            prodCateRepository.save(prodCate);
        }

        for(ProdSizeDto prodSizeDto : productDto.getSizeList()) {
            if(prodSizeDto.getSeqSize() != null) {
                prodSizeDto.setSeqProduct(newProduct.getSeqProduct());
                prodSizeRepository.save(modelMapper.map(prodSizeDto, ProdSize.class));
            }
        }*/

        return newProduct.getSeqWorkProduct();
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
