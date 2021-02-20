package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.PropertyConfig;
import kr.co.kimpoziben.test.dto.IjDto;
import kr.co.kimpoziben.dto.FileDto;
import kr.co.kimpoziben.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Controller
@AllArgsConstructor
@RequestMapping("/file")
public class FileController{

    FileService fileService;
    @Autowired
    private PropertyConfig propertyConfig;

    @PostMapping("/upload.do")
    //변경 : 복수 파일 MultipartHttpServletRequest multiFile 추가
    public String fileUpload(Model model,
                             IjDto ijDto, RedirectAttributes redirectAttr, MultipartHttpServletRequest multiFile) throws Exception {
        System.out.println("업로드 컨트롤러 호출 성공");
        List<MultipartFile> files = multiFile.getFiles("file");
        redirectAttr.addAttribute("seqAs", ijDto.getSeqAs());
        redirectAttr.addAttribute("passwordAs", ijDto.getPasswordAs());

        // 20201101 추가
        for(MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
            //질문 사항
            if(file.getOriginalFilename()=="" || file.getOriginalFilename().equals("")){
                System.out.println("통과");
                continue;
            }
            fileService.restore(file, ijDto);
        }

        return "redirect:/test/ij/detail.do";
    }

    @GetMapping("/download.do")
    public void download(HttpServletResponse response, IjDto ijDto, FileDto fileDto) throws UnsupportedEncodingException {

        fileDto = fileService.getFileInfo(fileDto.getIdFile(), fileDto.getSnFile());

        // 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
        //String fileName = "1df3a487-a885-4bf4-b590-c55009c01e3f1603980569546.jpg";
        //String saveFileName = "C:\\Users\\IKJIN\\IdeaProjects\\upload\\1df3a487-a885-4bf4-b590-c55009c01e3f1603980569546.jpg"; // 맥일 경우 "/tmp/connect.png" 로 수정
        //String contentType = "image/png";
        //int fileLength = 2246600;


        String fileName = fileDto.getOrgNmFile();
        String saveFileName = fileDto.getPathFile();
        String contentType = fileDto.getExtendsFile();
        Long fileLength = fileDto.getSizeFile();

        System.out.print(fileName);
        fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");


        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
                out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
    }

    @GetMapping("/allDownload.do")
    public void allDownload(HttpServletResponse response, FileDto fileDto) throws UnsupportedEncodingException {
        StringBuffer uploadPathBuf = new StringBuffer();
        uploadPathBuf.append(propertyConfig.getUploadPath());
        String zipFile = uploadPathBuf.toString() + propertyConfig.getFilePathSeperator() + "test.zip";
        //String zipFile = "C:/Users/IJ/IdeaProjects/test.zip";

        String downloadFileName = "Resultfile";

        List<String> sourceFiles = new ArrayList<String>();

        List<FileDto> fileDtoList = null;
        fileDtoList = fileService.getFileInfoList(fileDto.getIdFile());

        for(FileDto fileDtoResut : fileDtoList) {
            System.out.println(fileDtoResut.getPathFile());
            sourceFiles.add(fileDtoResut.getPathFile());
        }

        try{

            // ZipOutputStream을 FileOutputStream 으로 감쌈
            FileOutputStream fout = new FileOutputStream(zipFile);
            ZipOutputStream zout = new ZipOutputStream(fout);

            for(int i=0; i < sourceFiles.size(); i++){
                //원래 파일명으로 받아서 압축
                fileDto = fileService.getFileName(new File(sourceFiles.get(i)).getName());
                //여러개의 파일을 하나의 압축파일로 압축
                ZipEntry zipEntry = new ZipEntry(fileDto.getOrgNmFile());

                zout.putNextEntry(zipEntry);

                FileInputStream fin = new FileInputStream(sourceFiles.get(i));
                byte[] buffer = new byte[1024];
                int length;

                // input file을 1024바이트로 읽음, zip stream에 읽은 바이트를 씀
                while((length = fin.read(buffer)) > 0){
                    zout.write(buffer, 0, length);
                }

                zout.closeEntry();
                fin.close();
            }

            zout.close();

            response.setContentType("application/zip");
            response.addHeader("Content-Disposition", "attachment; filename=" + downloadFileName + ".zip");

            FileInputStream fis=new FileInputStream(zipFile);
            BufferedInputStream bis=new BufferedInputStream(fis); //질문
            ServletOutputStream so=response.getOutputStream(); //질문
            BufferedOutputStream bos=new BufferedOutputStream(so);

            byte[] data=new byte[2048];
            int input=0;

            while((input=bis.read(data))!=-1){
                bos.write(data,0,input);
                bos.flush();
            }

            if(bos!=null) bos.close();
            if(bis!=null) bis.close();
            if(so!=null) so.close();
            if(fis!=null) fis.close();

        } catch(IOException ioe){ }
    }


    /**
     * RP삭제
     * @param fileDto
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/delete.do")
    @ResponseBody
    public Object delete(@RequestBody FileDto fileDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {
        //System.out.println("삭제 컨르롤러 호출 성공");
        //List<FileDto> fileDtoList = new ArrayList<>();

        return fileService.updateYnDelFile(fileDto.getIdFile(), fileDto.getSnFile());
    }

    @PostMapping("/deleteComplete.do")
    @ResponseBody
    public Object deleteComplete(@RequestBody FileDto fileDto, HttpServletResponse response, RedirectAttributes redirectAttr) throws Exception {

        //List<FileDto> fileDtoList = new ArrayList<>();
        //fileDtoList = fileService.deleteCompleteFile(fileDto.getIdFile(), fileDto.getSnFile());

        return fileService.deleteCompleteFile(fileDto.getIdFile(), fileDto.getSnFile());
    }

}
