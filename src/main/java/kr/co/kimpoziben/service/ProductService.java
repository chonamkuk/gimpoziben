package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.*;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import kr.co.kimpoziben.dto.CategoryDto;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.SizeDto;
import kr.co.kimpoziben.dto.VendorDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private AttachService attachService;

    @Autowired
    private ProductRepository productRepository;

    ModelMapper modelMapper = null;

    private ProductService() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.typeMap(ProductDto.class, Product.class)
                .addMapping(ProductDto::getCateList, Product::setCateList)
                .addMapping(ProductDto::getSizeList, Product::setSizeList);
        modelMapper.typeMap(CategoryDto.class, Category.class);
        modelMapper.typeMap(VendorDto.class, Vendor.class);
        modelMapper.typeMap(SizeDto.class, Size.class);
    }

    public HashMap getList(Pageable pageble, HashMap<String,Object> searchMap) throws Exception {
        HashMap result = new HashMap();
        Page<Product> productPage = productRepository.findBySeqCategory(searchMap, pageble);
        List<ProductDto> productList = new ArrayList<>();
        for(Product product : productPage.getContent()) {
            productList.add(modelMapper.map(product, ProductDto.class));
        }
        result.put("resultPage", productPage);
        result.put("resultList", productList);
        return result;
    }

    @Transactional
    public Long save(ProductDto productDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "product");
            if (attachEntities != null) {
                productDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }

        Long seqProduct = productRepository.save(modelMapper.map(productDto, Product.class)).getSeqProduct();
        return seqProduct;
    }
}
