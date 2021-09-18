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
public class QnaDto {
    private Long seqQna;
    private String titleQna;
    private String dayQnaStart;
    private String dayQnaEnd;
    private String ynQnaOpen;
    private String ynDel;
    private String ynReply;
    private String textReply;
    private String descQna;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;

    private VendorDto vendor;
    private List<ProdCateDto> cateList = new ArrayList<ProdCateDto>();
    private List<ProdSizeDto> sizeList = new ArrayList<ProdSizeDto>();
    private List<ImageUploadDto> imageList = new ArrayList<ImageUploadDto>();



}
