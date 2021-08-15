package kr.co.kimpoziben.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoticeDto {
    private Long seqNotice;
    private String titleNotice;
    private String urlNotice;
    private String dayNoticeStart;
    private String dayNoticeEnd;
    private String ynNoticeMain;
    private String descNotice;
    private String idMainImg;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;

    private VendorDto vendor;
    private List<ProdCateDto> cateList = new ArrayList<ProdCateDto>();
    private List<ProdSizeDto> sizeList = new ArrayList<ProdSizeDto>();
    private List<ImageUploadDto> imageList = new ArrayList<ImageUploadDto>();



}
