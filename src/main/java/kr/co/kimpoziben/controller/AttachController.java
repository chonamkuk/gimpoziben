package kr.co.kimpoziben.controller;

import com.google.gson.JsonObject;
import kr.co.kimpoziben.domain.entity.AttachEntity;
import kr.co.kimpoziben.dto.AttachDto;
import kr.co.kimpoziben.dto.ImageUploadDto;
import kr.co.kimpoziben.service.AttachService;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
//import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/attach")
public class AttachController {
    private AttachService attachService;

    @GetMapping(value = "/resizeImgView.do", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] resizeImgView(@RequestParam("idAttach") String idAttach, @RequestParam(value = "snFileAttach", required = false, defaultValue = "0") int snFileAttach,
                         @RequestParam(value = "resizeWidth", required = false, defaultValue = "0") Integer resizeWidth) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            AttachDto attachDto = null;
            if(snFileAttach == 0) {
                attachDto = attachService.getAttachInfo(idAttach);
            } else {
                attachDto = attachService.getAttachInfo(idAttach, snFileAttach);
            }
            String imgPath = "";
            ImageIO.write(attachService.getResizeImg(attachDto.getPathFileAttach(), resizeWidth), "JPEG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            byteArrayOutputStream.close();
        }
    }

    @GetMapping(value = "/ckImgView.do")
    public void ckImgView(@RequestParam("idAttach") String idAttach, @RequestParam("snFileAttach") int snFileAttach,
                     HttpServletResponse res) throws Exception {
        ServletOutputStream servletOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            AttachDto attachDto = attachService.getAttachInfo(idAttach, snFileAttach);
            byteArrayOutputStream = new ByteArrayOutputStream();
            servletOutputStream = res.getOutputStream();

            ImageIO.write( attachService.getResizeImg(attachDto.getPathFileAttach(), 0)  , "JPEG", byteArrayOutputStream);
            byte imgBuf[] = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(imgBuf, 0, imgBuf.length);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            servletOutputStream.close();
            byteArrayOutputStream.close();
        }
    }

    @PostMapping(value="/ckImgUpload.do")
    public void ckImgUpload(HttpServletRequest req, HttpServletResponse resp,
                                      MultipartHttpServletRequest multiFile) throws Exception {
        JsonObject json = new JsonObject();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = null;
        OutputStream out = null;
        MultipartFile file = multiFile.getFile("upload");

        if(file != null){
            if(file.getSize() > 0 && StringUtils.hasLength(file.getName())){
                if(file.getContentType().toLowerCase().startsWith("image/")){
                    try{
                        String fileName = file.getName();
                        byte[] bytes = file.getBytes();

                        if(bytes.length <= 10485760) {
//                            String[] image = {Base64.encodeBase64String(file.getBytes())};
////                            String[] imageName = {file.getOriginalFilename()};
////                            String[] imageSize = {String.valueOf(file.getSize())};
////                            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "ckEditor");

                            List<ImageUploadDto> imageUploadList = new ArrayList<ImageUploadDto>();
                            ImageUploadDto imageUploadDto = new ImageUploadDto();
                            imageUploadDto.setImage(Base64.encodeBase64String(file.getBytes()));
                            imageUploadDto.setImageName(file.getOriginalFilename());
                            imageUploadDto.setImageSize(file.getSize());
                            imageUploadList.add(imageUploadDto);
                            List<AttachEntity> attachEntities = attachService.saveImage(imageUploadList, "ckEditor");

                            json.addProperty("uploaded", 1);
                            json.addProperty("fileName", file.getOriginalFilename());
                            json.addProperty("url", "/attach/resizeImgView.do?idAttach=" + attachEntities.get(0).getIdAttach() + "&snFileAttach=" + 1);
                        } else {
                            json.addProperty("uploaded", 0);
                            JsonObject errorMessage = new JsonObject();
                            errorMessage.addProperty("message", "10MB 이하의 파일을 올려주세요.");
                            json.add("error", errorMessage);
                        }
                        printWriter = resp.getWriter();
                        printWriter.println(json);
                        printWriter.flush();
                    }catch(IOException e){
                        e.printStackTrace();
                    }finally{
                        if(out != null){
                            out.close();
                        }
                        if(printWriter != null){
                            printWriter.close();
                        }
                    }
                }
            }
        }
    }

    @PostMapping(value = "/fileUpload.do")
    public void fileUpload(HttpServletRequest req, HttpServletResponse resp,
                            MultipartHttpServletRequest multiFile) throws Exception {

        List<MultipartFile> files = multiFile.getFiles("upload");
        for(MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
    }

    @DeleteMapping(value = "/delete.do")
    @ResponseBody
    public Object deleteAttach(AttachDto attachDto) {
        HashMap<String,Object> resultMap = new HashMap<>();
        try{
            attachService.updateDelYn(attachDto.getIdAttach(), attachDto.getSnFileAttach(), "admin"); //todo: 세션아이디 적용
            resultMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }
}
