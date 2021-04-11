package kr.co.kimpoziben.dto;

import lombok.*;

import java.time.LocalDateTime;

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
}
