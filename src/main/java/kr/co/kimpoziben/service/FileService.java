package kr.co.kimpoziben.service;

import kr.co.kimpoziben.PropertyConfig;
import kr.co.kimpoziben.domain.entity.FileEntity;
import kr.co.kimpoziben.domain.repository.FileRepository;
import kr.co.kimpoziben.test.dto.IjDto;
import kr.co.kimpoziben.dto.FileDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;



@AllArgsConstructor
@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    private PropertyConfig propertyConfig;

    public String restore(MultipartFile multipartFile, IjDto ijdto) throws Exception {
        String url = null, serverFilePath = "";;
        FileDto fileDto = null;
        try {
            // 파일 정보
            String originFilename = multipartFile.getOriginalFilename();
            System.out.println(".위치 : " + originFilename.lastIndexOf("."));
            System.out.println("파일 길이 : " + originFilename.length());

            String extName
                    = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
            Long size = multipartFile.getSize();
            StringBuffer uploadPathBuf = new StringBuffer();
            LocalDateTime localDateTime = LocalDateTime.now();

            uploadPathBuf.append(propertyConfig.getUploadPath());
            //파일경로 중간에 날짜 추가
            uploadPathBuf.append(propertyConfig.getFilePathSeperator() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

            Path uploadPath = Paths.get(uploadPathBuf.toString());
            if (Files.notExists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }

            // 서버에서 저장 할 파일 이름
            //20201102 날짜 폴더 추가

            String saveFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + extName;
            serverFilePath = uploadPathBuf.toString() + propertyConfig.getFilePathSeperator() + saveFileName; // 서버저장 경로

            Long idFile = ijdto.getSeqAs();

            System.out.println("idFile : " + idFile);
            System.out.println("originFilename : " + originFilename);
            System.out.println("extensionName : " + extName);
            System.out.println("size : " + size);
            System.out.println("saveFileName : " + saveFileName);
            System.out.println("serverFilePath : " + serverFilePath);

            int lastSn = 0;
            FileEntity lastFile = fileRepository.findTopByIdFileOrderBySnFileDesc(idFile);
            if(lastFile != null) lastSn = lastFile.getSnFile();


            writeFile(multipartFile, serverFilePath);

            fileDto = new FileDto();
            fileDto.setIdFile(idFile);
            fileDto.setSnFile(lastSn+1);
            fileDto.setOrgNmFile(originFilename);
            fileDto.setSrvNmFile(saveFileName);
            fileDto.setPathFile(serverFilePath);
            fileDto.setSizeFile(size);
            fileDto.setExtendsFile(extName);
            fileDto.setYnDel("N");
            fileDto.setRegisterFile("test");
            fileDto.setRegdtFile(LocalDateTime.now());
            fileDto.setModifierFile("test");
            fileDto.setModdtFile(LocalDateTime.now());
            System.out.println(fileDto);

            //fileRepository.updateYnDel(idFile);
            fileRepository.save(fileDto.toEntity());
        }
        catch (IOException e) {
            // 원래라면 RuntimeException 을 상속받은 예외가 처리되어야 하지만
            // 편의상 RuntimeException을 던진다.
            // throw new FileUploadException();
            throw new RuntimeException(e);
        }
        return url;
    }

    @Transactional
    public void updateDelYn(Long idFile) {

    }

    // 파일을 실제로 write 하는 메서드
    private boolean writeFile(MultipartFile multipartFile, String serverFilePath)
            throws IOException{
        boolean result = false;

        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(serverFilePath);
        fos.write(data);
        fos.close();

        return result;
    }
    /**
     * 첨부파일 아이디 생성
     * @param type
     * @return
     */
    private String generateIdAttach(String type) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));
        return time + "-" + type + "-"  + randomValue;
    }

    /**
     * 첨부파일 단건조회
     * @param idFile
     * @return
     */


//  단일 건 조회(다운로드 용)
    public FileDto getFileInfo(Long idFile,int snFile) {
        FileEntity fileEntity = fileRepository.findByIdFileAndSnFile(idFile, snFile);
        FileDto fileDto = FileDto.builder()
                .idFile(fileEntity.getIdFile())
                .snFile(fileEntity.getSnFile())
                .orgNmFile(fileEntity.getOrgNmFile())
                .srvNmFile(fileEntity.getSrvNmFile())
                .pathFile(fileEntity.getPathFile())
                .sizeFile(fileEntity.getSizeFile())
                .ynDel(fileEntity.getYnDel())
                .registerFile(fileEntity.getRegisterFile())
                .regdtFile(fileEntity.getRegdtFile())
                .modifierFile(fileEntity.getModifierFile())
                .moddtFile(fileEntity.getModdtFile())
                .build();

        return fileDto;
    }

    //  서버파일이름으로 원래 파일 명 조회
    public FileDto getFileName(String srvNmFile) {
        FileEntity fileEntity = fileRepository.findTopBySrvNmFile(srvNmFile);
        FileDto fileDto = FileDto.builder()
                .idFile(fileEntity.getIdFile())
                .snFile(fileEntity.getSnFile())
                .orgNmFile(fileEntity.getOrgNmFile())
                .srvNmFile(fileEntity.getSrvNmFile())
                .pathFile(fileEntity.getPathFile())
                .sizeFile(fileEntity.getSizeFile())
                .ynDel(fileEntity.getYnDel())
                .registerFile(fileEntity.getRegisterFile())
                .regdtFile(fileEntity.getRegdtFile())
                .modifierFile(fileEntity.getModifierFile())
                .moddtFile(fileEntity.getModdtFile())
                .build();

        return fileDto;
    }
    // 20201101 문익진 추가
    public List<FileDto> getFileInfoList(Long idFile) {
        List<FileEntity> fileEntities = fileRepository.findByIdFileAndYnDel(idFile, "N");

        List<FileDto> fileDtoList = new ArrayList<>();

        for(FileEntity fileEntity : fileEntities) {
            FileDto fileDto = FileDto.builder()
                    .idFile(fileEntity.getIdFile())
                    .snFile(fileEntity.getSnFile())
                    .orgNmFile(fileEntity.getOrgNmFile())
                    .srvNmFile(fileEntity.getSrvNmFile())
                    .pathFile(fileEntity.getPathFile())
                    .sizeFile(fileEntity.getSizeFile())
                    .ynDel(fileEntity.getYnDel())
                    .registerFile(fileEntity.getRegisterFile())
                    .regdtFile(fileEntity.getRegdtFile())
                    .modifierFile(fileEntity.getModifierFile())
                    .moddtFile(fileEntity.getModdtFile())
                    .build();

            fileDtoList.add(fileDto);
        }

        return fileDtoList;
    }
    
    public String updateYnDelFile(Long idFile, int snFile) throws Exception {
        FileDto fileDto = null;

        fileDto = getFileInfo(idFile,snFile);
        String pathFile = fileDto.getPathFile();
        File file = new File(pathFile);
        String result = "";

        try { //try catch 추가
            if( file.exists() ){
                if(file.delete()){
                    fileRepository.updateYnDelFile(idFile, snFile);
                    result = "success";
                    System.out.println("파일삭제 성공");
                } else{
                    result = "파일이 사용되고있어 삭제에 실패하였습니다.";
                }
            }
            else{
                result = "서버에 파일이 존재하지 않습니다. 관리자에게 문의하세요.";
            }
        }
        catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", file);
            result = "경로가 맞지 않아 파일 삭제에 실패하였습니다.";
        }
/*        catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", file);
            result = "디렉토리가 비어있지 않아 삭제에 실패하였습니다.";}*/
        catch (IOException x) {
            // File permission problems are caught here. System.err.println(x);
            result = "파일 삭제에 실패하였습니다. IOException";
        }

        return result;
    }


    public String deleteCompleteFile(Long idFile, int snFile) throws Exception {
        FileDto fileDto = null;

        fileDto = getFileInfo(idFile,snFile);
        String pathFile = fileDto.getPathFile();
        File file = new File(pathFile);
        String result = "";

        try { //try catch 추가
            if( file.exists() ){
                if(file.delete()){
                    fileRepository.deleteAllByIdFileAndSnFile(idFile, snFile);
                    result = "success";
                    System.out.println("파일 완전 삭제 성공");
                } else{
                    result = "파일이 사용되고있어 삭제에 실패하였습니다.";
                }
            }
            else{
                result = "서버에 파일이 존재하지 않습니다. 관리자에게 문의하세요.";
            }
        }
        catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", file);
            result = "경로가 맞지 않아 파일 삭제에 실패하였습니다.";
        }
/*        catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", file);
            result = "디렉토리가 비어있지 않아 삭제에 실패하였습니다.";}*/
        catch (IOException x) {
            // File permission problems are caught here. System.err.println(x);
            result = "파일 삭제에 실패하였습니다. IOException";
        }

        return result;
    }



}
