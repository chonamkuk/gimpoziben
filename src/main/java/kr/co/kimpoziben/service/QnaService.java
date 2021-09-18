package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.domain.entity.Notice;
import kr.co.kimpoziben.domain.entity.Qna;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.repository.QnaRepository;
import kr.co.kimpoziben.domain.repository.ProductRepository;
import kr.co.kimpoziben.domain.repository.ProductWorkRepository;
import kr.co.kimpoziben.dto.QnaDto;
import kr.co.kimpoziben.dto.ProductDto;
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
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;


    ModelMapper modelMapper = null;
    private Object ProductWorkDto;

    private QnaService() {
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




    public HashMap findQnaList() throws Exception {
        List<QnaDto> qnaList = new ArrayList<>();
        HashMap result = new HashMap();
        for(Qna qna : qnaRepository.findAll()) {
            QnaDto qnaDto = new QnaDto();
            qnaDto.setTitleQna(qna.getTitleQna());
            qnaDto.setSeqQna(qna.getSeqQna());
            qnaDto.setRegister(qna.getRegister());
            qnaDto.setRegDt(qna.getRegDt());
            qnaDto.setYnDel(qna.getYnDel());
            qnaDto.setYnQnaOpen(qna.getYnQnaOpen());
            qnaDto.setYnReply(qna.getYnReply());
            qnaDto.setTextReply(qna.getYnReply());
            qnaList.add(qnaDto);
        }
        result.put("qnaList", qnaList);

        return result;
    }

    @Transactional
    public Long save(QnaDto qnaDto) {
       /* if(qnaDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(qnaDto.getImageList(), "product");
            if (attachEntities != null) {
                qnaDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }*/


        qnaDto.setRegDt(LocalDateTime.now());

        if (qnaDto.getYnDel()==null){qnaDto.setYnDel("N");}
        if (qnaDto.getYnQnaOpen()==null){qnaDto.setYnDel("N");}
        if (qnaDto.getYnReply()==null){qnaDto.setYnDel("N");}

        Qna qna = qnaRepository.save(modelMapper.map(qnaDto, Qna.class));
        return qna.getSeqQna();
    }

    public QnaDto getQnaDetail(Long seqQna) throws  Exception {
        Qna qna = qnaRepository.findById(seqQna).orElse(null);
        if(qna != null) {
            QnaDto qnaDto = modelMapper.map(qna, QnaDto.class);
            return qnaDto;
        }

        return null;
    }

    @Transactional
    public Long update(QnaDto qnaDto) {
      /*  if(qnaDto.getImageList().size() > 0) {
            List<AttachEntity> attachEntities = attachService.saveImage(qnaDto.getImageList(), "product", qnaDto.getIdMainImg());
            if (attachEntities != null) {
                qnaDto.setIdMainImg(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }
*/
        Qna newProduct = qnaRepository.save(modelMapper.map(qnaDto, Qna.class));


        return newProduct.getSeqQna();
    }
}
