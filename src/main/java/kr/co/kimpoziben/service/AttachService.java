package kr.co.kimpoziben.service;

import kr.co.kimpoziben.config.PropertyConfig;
import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.domain.repository.AttachRepository;
import kr.co.kimpoziben.dto.ImageUploadDto;
import kr.co.kimpoziben.dto.AttachDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AttachService {
    private AttachRepository attachRepository;

    @Autowired
    private PropertyConfig propertyConfig;

    @Transactional
    public String saveAttachInfo(AttachDto attachDto) {
        return attachRepository.save(attachDto.toEntity()).getIdAttach();
    }

    /**
     * 첨부파일 단건조회
     * @param idAttach
     * @param snFileAttach
     * @return
     */
    public AttachDto getAttachInfo(String idAttach, int snFileAttach) {
        AttachEntity attachEntity = attachRepository.findByIdAttachAndSnFileAttach(idAttach, snFileAttach);
        AttachDto attachDto = AttachDto.builder()
                .idAttach(attachEntity.getIdAttach())
                .snFileAttach(attachEntity.getSnFileAttach())
                .nmOrgFileAttach(attachEntity.getNmOrgFileAttach())
                .nmSrvFileAttach(attachEntity.getNmSrvFileAttach())
                .pathFileAttach(attachEntity.getPathFileAttach())
                .sizeFileAttach(attachEntity.getSizeFileAttach())
                .extendsAttach(attachEntity.getExtendsAttach())
                .ordrAttach(attachEntity.getOrdrAttach())
                .ynDel(attachEntity.getYnDel())
                .registerAttach(attachEntity.getRegisterAttach())
                .regdtAttach(attachEntity.getRegdtAttach())
                .modifierAttach(attachEntity.getModifierAttach())
                .moddtAttach(attachEntity.getModdtAttach())
                .build();

        return attachDto;
    }

    /**
     * 첨부파일 단건조회
     * del_yn = 'N' 인 것중 attach_ordr 이 가장 작은 건 = 메인이미지
     * @param idAttach
     * @return
     */
    public AttachDto getAttachInfo(String idAttach) {
        AttachEntity attachEntity = attachRepository.findTopByIdAttachAndYnDelOrderByOrdrAttachAsc(idAttach, "N");
        AttachDto attachDto = AttachDto.builder()
                .idAttach(attachEntity.getIdAttach())
                .snFileAttach(attachEntity.getSnFileAttach())
                .nmOrgFileAttach(attachEntity.getNmOrgFileAttach())
                .nmSrvFileAttach(attachEntity.getNmSrvFileAttach())
                .pathFileAttach(attachEntity.getPathFileAttach())
                .sizeFileAttach(attachEntity.getSizeFileAttach())
                .extendsAttach(attachEntity.getExtendsAttach())
                .ordrAttach(attachEntity.getOrdrAttach())
                .ynDel(attachEntity.getYnDel())
                .registerAttach(attachEntity.getRegisterAttach())
                .regdtAttach(attachEntity.getRegdtAttach())
                .modifierAttach(attachEntity.getModifierAttach())
                .moddtAttach(attachEntity.getModdtAttach())
                .build();

        return attachDto;
    }

    public List<AttachDto> getAttachInfoList(String idAttach) {
        List<AttachEntity> attachEntities = attachRepository.findByIdAttachAndYnDelOrderByOrdrAttach(idAttach, "N");

        List<AttachDto> attachDtoList = new ArrayList<>();

        for(AttachEntity attachEntity : attachEntities) {
            AttachDto attachDto = AttachDto.builder()
                    .idAttach(attachEntity.getIdAttach())
                    .snFileAttach(attachEntity.getSnFileAttach())
                    .nmOrgFileAttach(attachEntity.getNmOrgFileAttach())
//                    .nmSrvFileAttach(attachEntity.getNmSrvFileAttach())
//                    .pathFileAttach(attachEntity.getPathFileAttach())
                    .sizeFileAttach(attachEntity.getSizeFileAttach())
                    .ordrAttach(attachEntity.getOrdrAttach())
                    .ynDel(attachEntity.getYnDel())
                    .registerAttach(attachEntity.getRegisterAttach())
                    .regdtAttach(attachEntity.getRegdtAttach())
                    .modifierAttach(attachEntity.getModifierAttach())
                    .moddtAttach(attachEntity.getModdtAttach())
                    .build();

            attachDtoList.add(attachDto);
        }

        return attachDtoList;
    }

    /**
     * 이미지 물리경로를 받아서 사이즈 조절 후 리턴, 썸네일 표현할 때 사용
     * @param srcImgPath
     * @param resizeWidth
     * @return
     * @throws Exception
     */
    public BufferedImage getResizeImg(String srcImgPath, int resizeWidth) throws Exception {

        Path path = Paths.get(srcImgPath);
        BufferedImage resizeImg = null;
        if(Files.isRegularFile(path)) {

            // 원본 이미지 read
            BufferedImage srcImg = ImageIO.read(Files.newInputStream(path));
            resizeImg = resizeImage(resizeWidth, srcImg);
//            resizeImg = srcImg;

        } else {
            throw new Exception("변환대상 이미지 파일이 존재하지 않습니다.("+path.getFileName()+")");
        }
        return resizeImg;
    }

    /**
     * 버퍼이미지를 받아서 사이즈 조절 후 리턴
     * @param resizeWidth
     * @param srcImg
     * @return
     */
    public BufferedImage resizeImage(int resizeWidth, BufferedImage srcImg) {
        BufferedImage resizeImg;
        int srcImgWidth = srcImg.getWidth(); // 비율 계산용
        int srcImgHeight = srcImg.getHeight();// 비율 계산용

        // 가로 기준으로 줄인다면
        int resizeImgWidth = resizeWidth == 0 ? srcImgWidth : resizeWidth; // 줄이고 싶은 가로사이즈, 0이면 원본사이즈
        int resizeImgHeight = resizeWidth == 0 ? srcImgHeight : (srcImgHeight*resizeImgWidth)/srcImgWidth; // 원본비율 대비 줄이는 세로사이즈

        // 리사이즈 이미지 객체
        resizeImg = new BufferedImage(resizeImgWidth, resizeImgHeight,BufferedImage.TYPE_INT_RGB);

        // 원본 이미지를 리사이즈 객체에 그린다
        Graphics2D g2 = resizeImg.createGraphics();
        g2.drawImage(srcImg, 0, 0, resizeImgWidth, resizeImgHeight, null);
        g2.dispose();
        return resizeImg;
    }

    private String generateIdAttach(String type) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));
        return time + "-" + type + "-"  + randomValue;
    }

    @Transactional
    public void updateDelYn(String idAttach, int snFileAttach, String modifier) {
        AttachDto attachDto = this.getAttachInfo(idAttach, snFileAttach);
        attachDto.setModdtAttach(LocalDateTime.now());
        attachDto.setYnDel("Y");
        attachDto.setModifierAttach(modifier);
//        this.saveAttachInfo(attachDto);
        attachRepository.save(attachDto.toEntity());
    }

    @Transactional
   public List<AttachEntity> saveImage(List<ImageUploadDto> imageUploadList, String type) {
        String idAttach = generateIdAttach(type);
        return this.saveImage(imageUploadList, type, idAttach);
    }

    @Transactional
    public List<AttachEntity> saveImage(List<ImageUploadDto> imageUploadList, String type, String idAttach) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 첨부파일 DB저장 변수
        List<AttachEntity> attachEntities = new ArrayList<>();
        AttachDto attachDto = null;
        String extension = "", serverFileNm = "", serverFilePath = "";
        int lastSn = 0;
        if(idAttach.isEmpty()) idAttach = generateIdAttach(type);
        AttachEntity lastAttach = attachRepository.findTopByIdAttachOrderBySnFileAttachDesc(idAttach);
        if(lastAttach != null) lastSn = lastAttach.getSnFileAttach();

        // 이미지 생성관련 변수
        String data = "";
        byte[] imageBytes = null;
        StringBuffer uploadPathBuf = new StringBuffer();
        uploadPathBuf.append(propertyConfig.getUploadPath());
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + type);
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getYear());
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getMonthValue());
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getDayOfMonth());
        Path uploadPath = Paths.get(uploadPathBuf.toString());

        if (Files.notExists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            ImageUploadDto imageUploadDto = null;
            for(int i=0; i<imageUploadList.size(); i++) {
                imageUploadDto = imageUploadList.get(i);
                data = imageUploadDto.getImage();
                imageBytes = DatatypeConverter.parseBase64Binary(data);
                BufferedImage originImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

                // 파일생성
                extension = imageUploadDto.getImageName().substring(imageUploadDto.getImageName().lastIndexOf("."), imageUploadDto.getImageName().length()); // 확장자
                serverFileNm = UUID.randomUUID().toString() + System.currentTimeMillis() + extension; // 서버저장 파일명
                serverFilePath = uploadPathBuf.toString() + propertyConfig.getFilePathSeperator() + serverFileNm; // 서버저장 경로
                System.out.println("serverFilePath ;; " + serverFilePath);


                ImageIO.write(originImage, extension.substring(1), new File(serverFilePath));

                // 첨부파일 저장 값
                attachDto = new AttachDto();
                attachDto.setIdAttach(idAttach);
                attachDto.setSnFileAttach(i+1+lastSn);
                attachDto.setNmOrgFileAttach(imageUploadDto.getImageName());
                attachDto.setNmSrvFileAttach(serverFileNm);
                attachDto.setPathFileAttach(serverFilePath);
                attachDto.setSizeFileAttach(Long.valueOf(imageUploadDto.getImageSize()));
                attachDto.setExtendsAttach(extension);
                attachDto.setOrdrAttach(imageUploadDto.getOrdrAttach());
                attachDto.setYnDel("N");
                attachDto.setRegisterAttach("test"); // todo: 사용자정보 저장?
                attachDto.setRegdtAttach(LocalDateTime.now());

                attachEntities.add(attachDto.toEntity());
            }

            attachRepository.saveAll(attachEntities);
            return attachEntities;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
