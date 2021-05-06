package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.SizeRepository;
import kr.co.kimpoziben.dto.SizeDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    public List<SizeDto> getList() throws  Exception {
        List<SizeDto> sizeList = new ArrayList<>();

        for(Size size : sizeRepository.findAll(Sort.by(Sort.Direction.ASC, "ordrSize"))) {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setSeqSize(size.getSeqSize());
            sizeDto.setNmSize(size.getNmSize());
            if(size.getUpperSize() != null) {
                sizeDto.setUpperSize(size.getUpperSize());
            }
            sizeList.add(sizeDto);
        }

        return sizeList;
    }

    public List<SizeDto> getUpperList() throws Exception {
        List<SizeDto> sizeList = new ArrayList<>();
        for(Size size : sizeRepository.findAllByUpperSizeIsNull(Sort.by(Sort.Direction.ASC, "ordrSize"))) {
            SizeDto sizeDto = new SizeDto();
            sizeDto.setSeqSize(size.getSeqSize());
            sizeDto.setNmSize(size.getNmSize());
            sizeDto.setSubSizeList(size.getSubSizeList());
            sizeList.add(sizeDto);
        }

        return sizeList;
    }

    public SizeDto getSizeDetail(Long seqSize) throws  Exception {
        Size size = sizeRepository.findById(seqSize).get();

        SizeDto sizeDto = new SizeDto();
        sizeDto.setSeqSize(size.getSeqSize());
        sizeDto.setNmSize(size.getNmSize());

        return sizeDto;
    }

}
