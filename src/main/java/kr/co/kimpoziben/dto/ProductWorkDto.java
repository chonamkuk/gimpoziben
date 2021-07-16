package kr.co.kimpoziben.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductWorkDto {
    private Long seqWorkProduct;
    private String titleWorkProduct;
    private String dayWorkProduct;
    private String descWorkProduct;
    private Integer priceWorkProduct;
    private Integer countWorkProduct;
    private String typeWorkProduct;
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
