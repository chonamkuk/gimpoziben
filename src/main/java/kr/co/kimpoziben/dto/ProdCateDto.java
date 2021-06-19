package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.Category;
import kr.co.kimpoziben.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProdCateDto {
    private Long seqProduct;
    private Long seqCategory;
}
