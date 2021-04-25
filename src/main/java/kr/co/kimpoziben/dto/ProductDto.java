package kr.co.kimpoziben.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {
    private Long seqProduct;
    private String nmProduct;
    private String descProduct;
    private String sizeProduct;
    private String ynDisplay;
    private String ynSoldout;
    private String idMainImg;
    private String idDescImg;
    private String nmManufac;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;

    private VendorDto vendor;
    private List<ProdCateDto> cateList = new ArrayList<ProdCateDto>();
    private List<ProdSizeDto> sizeList = new ArrayList<ProdSizeDto>();
}
