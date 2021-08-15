package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.domain.entity.Notice;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.ProductWork;
import kr.co.kimpoziben.domain.repository.*;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.ProductWorkDto;
import kr.co.kimpoziben.dto.NoticeDto;
import lombok.AllArgsConstructor;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
//@org.springframework.transaction.annotation.Transactional
public class NoticeService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductWorkRepository productWorkRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private SizeService sizeService;

    ModelMapper modelMapper = null;
    private Object ProductWorkDto;

    private NoticeService() {
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




    public HashMap findNoticeList() throws Exception {
        List<NoticeDto> noticeList = new ArrayList<>();
        HashMap result = new HashMap();
        for(Notice notice : noticeRepository.findAll()) {
            NoticeDto noticeDto = new NoticeDto();
            noticeDto.setTitleNotice(notice.getTitleNotice());
            noticeDto.setSeqNotice(notice.getSeqNotice());
            noticeDto.setRegister(notice.getRegister());
            noticeDto.setRegDt(notice.getRegDt());
            noticeDto.setDayNoticeStart(notice.getDayNoticeStart());
            noticeDto.setDayNoticeEnd(notice.getDayNoticeEnd());
            noticeDto.setIdMainImg(notice.getIdMainImg());
            noticeList.add(noticeDto);
        }
        result.put("noticeList", noticeList);

        return result;
    }

    @Transactional
    public Long save(NoticeDto noticeDto) {
        if(noticeDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(noticeDto.getImageList(), "product");
            if (attachEntities != null) {
                noticeDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }

        /** todo: 비어있는 값들이 null로 입력되는 경우 수정해야 함
         *         into
         *             gps_product
         *             (price_buy, product_color, product_desc, main_img_id, moddt, modifier, product_nm, regdt, register, price_sell, product_title, vendor_seq, display_yn, soldout_yn)
         *         values
         *             (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
         */
        noticeDto.setRegDt(LocalDateTime.now());
        Notice notice = noticeRepository.save(modelMapper.map(noticeDto, Notice.class));
        return notice.getSeqNotice();
    }

    public ProductWorkDto getProductDetail(Long seqWorkProduct) throws  Exception {
        ProductWork productWork = productWorkRepository.findById(seqWorkProduct).orElse(null);
        if(productWork != null) {
            ProductWorkDto productWorkDto = modelMapper.map(productWork, ProductWorkDto.class);
            return productWorkDto;
        }

        return null;
    }

    @Transactional
    public Long update(ProductWorkDto productWorkDto) {
        if(productWorkDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(productWorkDto.getImageList(), "product", productWorkDto.getIdMainImg());
            if (attachEntities != null) {
                productWorkDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }

        ProductWork newProduct = productWorkRepository.save(modelMapper.map(productWorkDto, ProductWork.class));


        return newProduct.getSeqWorkProduct();
    }
}
