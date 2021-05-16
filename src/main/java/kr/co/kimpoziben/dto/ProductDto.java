package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.ProdSize;
import kr.co.kimpoziben.domain.entity.Product;
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
    private String titleProduct;
    private String nmProduct;
    private String descProduct;
    private String colorProduct;
    private Integer buyPrice;
    private Integer sellPrice;
    private String ynDisplay;
    private String ynSoldOut;
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
