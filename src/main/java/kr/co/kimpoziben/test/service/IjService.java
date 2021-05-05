package kr.co.kimpoziben.test.service;

import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.service.AttachService;
import kr.co.kimpoziben.test.domain.entity.IjEntity;
import kr.co.kimpoziben.test.domain.repository.IjRepository;
import kr.co.kimpoziben.test.dto.IjDto;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.util.AES256Util;
import kr.co.kimpoziben.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class IjService {
    @Autowired
    AES256Util aes;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private IjRepository ijRepository;
    private AttachService attachService;


    @Transactional
    public Long saveAs(IjDto ijDto, String[] image, String[] imageName, String[] imageSize) {
//        if(image != null) {
//            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "as");
//            if (attachEntities != null) {
//                ijDto.setIdAttach(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
//            }
//        }
        Long seqAs = ijRepository.save(ijDto.toEntity()).getSeqAs();
        return seqAs;

    }
    //리스트 호출
    public HashMap getAsList(Pageable pageble, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();

        Page<IjEntity> asEntityPage = null;

        asEntityPage = ijRepository.findAll((Specification<IjEntity>) SearchSpec.searchLike3(searchDto), pageble);
        List<IjEntity> asEntities = asEntityPage.getContent();
        List<IjDto> asDtoList = new ArrayList<>();
        for(IjEntity ijEntity : asEntities) {
            IjDto asDto = IjDto.builder()
                    .seqAs(ijEntity.getSeqAs())
                    .titleAs(ijEntity.getTitleAs())
                    .resStartDtAs(ijEntity.getResStartDtAs())
                    .resEndDtAs(ijEntity.getResEndDtAs())
                    .contentsAs(ijEntity.getContentsAs())
                    .noTelAs(aes.decrypt(ijEntity.getNoTelAs()))
                    .latitudeAs(ijEntity.getLatitudeAs())
                    .longitudeAs(ijEntity.getLongitudeAs())
                    .locationAs(ijEntity.getLocationAs())
                    .dtlLocationAs(ijEntity.getDtlLocationAs())
                    .passwordAs(ijEntity.getPasswordAs())
                    .statAs(ijEntity.getStatAs())
                    .commentAs(ijEntity.getCommentAs())
                    .idAttach(ijEntity.getIdAttach())
                    .writerAs(ijEntity.getWriterAs())
                    .regdtAs(ijEntity.getRegdtAs())
                    .modifierAs(ijEntity.getModifierAs())
                    .moddtAs(ijEntity.getModdtAs())
                    .ynDel(ijEntity.getYnDel())
                    .build();

            asDtoList.add(asDto);
        }
        result.put("asEntityPage", asEntityPage);
        result.put("asDtoList", asDtoList);

        return result;
    }

    public boolean passwordChk(IjDto ijDto) throws Exception {
        return passwordEncoder.matches(ijDto.getPasswordAs(),
                Optional.ofNullable(this.getAsDetail(ijDto.getSeqAs())).map(IjDto::getPasswordAs).orElse(""));
    }

    public IjDto getAsDetail(Long seqAs) throws Exception {
        Optional<IjEntity> asEntityWrapper = ijRepository.findById(seqAs);
        IjEntity ijEntity = asEntityWrapper.get();

        IjDto ijDto = IjDto.builder()
                .seqAs(ijEntity.getSeqAs())
                .titleAs(ijEntity.getTitleAs())
                .resStartDtAs(ijEntity.getResStartDtAs())
                .resEndDtAs(ijEntity.getResEndDtAs())
                .contentsAs(ijEntity.getContentsAs())
                .noTelAs(aes.decrypt(ijEntity.getNoTelAs()))
                .latitudeAs(ijEntity.getLatitudeAs())
                .longitudeAs(ijEntity.getLongitudeAs())
                .locationAs(ijEntity.getLocationAs())
                .dtlLocationAs(ijEntity.getDtlLocationAs())
                .passwordAs(ijEntity.getPasswordAs())
                .statAs(ijEntity.getStatAs())
                .commentAs(ijEntity.getCommentAs())
                .idAttach(ijEntity.getIdAttach())
                .writerAs(ijEntity.getWriterAs())
                .regdtAs(ijEntity.getRegdtAs())
                .modifierAs(ijEntity.getModifierAs())
                .moddtAs(ijEntity.getModdtAs())
                .ynDel(ijEntity.getYnDel())
                .build();

        return ijDto;
    }
    @Transactional
    public Long updateAs(IjDto ijDto, String[] image, String[] imageName, String[] imageSize) throws Exception {
//        if(image != null) {
//            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "as", ijDto.getIdAttach());
//            if (attachEntities != null) {
//                ijDto.setIdAttach(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
//            }
//        }
        IjDto oldData = this.getAsDetail(ijDto.getSeqAs());
        ijDto.setStatAs(oldData.getStatAs());
        ijDto.setCommentAs(oldData.getCommentAs());
        Long seqAs = ijRepository.save(ijDto.toEntity()).getSeqAs();

        return seqAs;
    }
    @Transactional
    public Long updateAsAdmin(IjDto ijDto) throws Exception {
        Long seqAs = ijRepository.save(ijDto.toEntity()).getSeqAs();
        return seqAs;
    }

    @Transactional
    public void updateYnDel(Long seqAs) throws Exception {
        ijRepository.updateYnDel(seqAs);
    }

    @Transactional
    public void delete(Long id) {
        ijRepository.deleteById(id);
    }
}
