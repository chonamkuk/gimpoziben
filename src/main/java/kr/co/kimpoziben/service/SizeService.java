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
        modelMapper.typeMap(Size.class, SizeDto.class);
    }

    public List<SizeDto> getList() throws  Exception {
        List<SizeDto> sizeList = new ArrayList<>();

        for(Size size : sizeRepository.findAll(Sort.by(Sort.Direction.ASC, "ordrSize"))) {
            sizeList.add(modelMapper.map(size, SizeDto.class));
        }

        return sizeList;
    }

    public List<SizeDto> findBySeqProduct(Long seqProduct) throws Exception {
        List<SizeDto> sizeList = new ArrayList<>();

        for(Size size : sizeRepository.findBySeqProduct(seqProduct)) {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setSeqSize(size.getSeqSize());
            sizeDto.setNmSize(size.getNmSize());
            sizeList.add(sizeDto);
        }

        return sizeList;
    }

    public List<SizeDto> getHierarchyList() throws Exception {
        List<SizeDto> sizeDtoList = new ArrayList<>();
        for(Size size : sizeRepository.findAll(Sort.by(Sort.Direction.ASC, "ordrSize"))) {
            sizeDtoList.add(modelMapper.map(size, SizeDto.class));
        }

        List<SizeDto> hierarchyList = new ArrayList<>();
        for(SizeDto sizeDto : sizeDtoList) {
            if(sizeDto.getUpperSize() == null) {
                for(SizeDto subSizeDto : sizeDtoList) {
                    if(subSizeDto.getUpperSize() != null && sizeDto.getSeqSize() == subSizeDto.getUpperSize().getSeqSize()) {
                        sizeDto.addSubSize(subSizeDto);
                    }
                }

                hierarchyList.add(sizeDto);
            }
        }

        return hierarchyList;
    }

    public SizeDto getSizeDetail(Long seqSize) throws  Exception {
        Size size = sizeRepository.findById(seqSize).get();

        SizeDto sizeDto = new SizeDto();
        sizeDto.setSeqSize(size.getSeqSize());
        sizeDto.setNmSize(size.getNmSize());

        return sizeDto;
    }

}
