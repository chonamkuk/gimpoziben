package kr.co.kimpoziben.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderProductDto {
    private Long seqProductOrder;
    private Long seqOrder;
    private String cartId;
    private Long seqProduct;
    private String nmProduct;
    private Long seqSize;
    private String nmSize;
    private String idMainImg;
    private Long amountOrder;
    private Long priceOrder;

    public Long getProductTotalPrice() {
        return this.amountOrder * this.priceOrder;
    }
}
