package kr.co.kimpoziben.test.service;

import kr.co.kimpoziben.test.domain.entity.RpEntity;
import kr.co.kimpoziben.test.domain.repository.RpRepository;
import kr.co.kimpoziben.test.dto.RpDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RpService {
    private RpRepository rpRepository;


    public List<RpDto> getRpList(Long idRp) {
        List<RpEntity> rpEntities = rpRepository.findByIdRpAndYnDelRpOrderBySeqRpDesc(idRp,"N");

        List<RpDto> rpDtoList = new ArrayList<>();

        for(RpEntity rpEntity : rpEntities) {
            RpDto rpDto = RpDto.builder()
                    .idRp(rpEntity.getIdRp())
                    .seqRp(rpEntity.getSeqRp())
                    .commentRp(rpEntity.getCommentRp())
                    .regDtRp(rpEntity.getRegDtRp())
                    .userRp(rpEntity.getUserRp())
                    .ynDelRp(rpEntity.getYnDelRp())
                    .build();

            rpDtoList.add(rpDto);
        }

        return rpDtoList;
    }

    public Long saveAs(RpDto rpDto) {
        Long SeqRp = rpRepository.save(rpDto.toEntity()).getSeqRp();
        return SeqRp;
    }

    public Long updateRp(RpDto rpDto) {
        Long idRp = rpRepository.save(rpDto.toEntity()).getIdRp();
        return idRp;
    }

    @Transactional
    public void updateYnDelRp(Long seqRp) throws Exception {
        rpRepository.updateYnDelRp(seqRp);
    }

    @Transactional
    public void updateCommentRp(Long seqRp, String commentRp) throws Exception {
        rpRepository.updateCommentRp(seqRp,commentRp);
    }

}
