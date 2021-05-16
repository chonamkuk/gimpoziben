package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.SizeRepository;
import kr.co.kimpoziben.dto.ProductDto;
import kr.co.kimpoziben.dto.SizeDto;
import lombok.AllArgsConstructor;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    ModelMapper modelMapper = null;

    private SizeService() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setPropertyCondition(new Condition<Object, Object>() {
            @Override
            public boolean applies(MappingContext<Object, Object> mappingContext) {
                return !(mappingContext.getSource() instanceof PersistentCollection);
            }
        });
        modelMapper.typeMap(SizeDto.class, Size.class);
    }

    public List<SizeDto> getList() throws  Exception {
        List<SizeDto> sizeList = new ArrayList<>();

        for(Size size : sizeRepository.findAll(Sort.by(Sort.Direction.ASC, "ordrSize"))) {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setSeqSize(size.getSeqSize());
            sizeDto.setNmSize(size.getNmSize());
//            if(size.getUpperSize() != null) {
//                sizeDto.setUpperSize(size.getUpperSize());
//            }
            sizeList.add(sizeDto);
        }

        return sizeList;
    }

    public List<SizeDto> findBySeqProduct(Long seqProduct) throws Exception {
        List<SizeDto> sizeList = new ArrayList<>();

        for(Size size : sizeRepository.findBySeqProduct(seqProduct)) {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setSeqSize(size.getSeqSize());
            sizeDto.setNmSize(size.getNmSize());
//            if(size.getUpperSize() != null) {
//                sizeDto.setUpperSize(size.getUpperSize());
//            }
            sizeList.add(sizeDto);
        }

        return sizeList;
    }

    public List<SizeDto> getUpperList() throws Exception {
        List<SizeDto> sizeDtoList = new ArrayList<>();
        List<Size> sizeList = sizeRepository.findAllByUpperSizeIsNull(Sort.by(Sort.Direction.ASC, "ordrSize"));
        for(Size size : sizeList) {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setSeqSize(size.getSeqSize());
            sizeDto.setNmSize(size.getNmSize());
            sizeDto.setSubSizeList(size.getSubSizeList().stream()
                    .map(subSize -> modelMapper.map(subSize, SizeDto.class))
                    .collect(Collectors.toList())
            );
            sizeDtoList.add(sizeDto);
//            sizeDtoList.add(modelMapper.map(size, SizeDto.class));
        }

        return sizeDtoList;
    }

    public SizeDto getSizeDetail(Long seqSize) throws  Exception {
        Size size = sizeRepository.findById(seqSize).get();

        SizeDto sizeDto = new SizeDto();
        sizeDto.setSeqSize(size.getSeqSize());
        sizeDto.setNmSize(size.getNmSize());

        return sizeDto;
    }

}
