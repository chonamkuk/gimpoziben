package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.entity.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProdSizeDto {
    private ProductDto product;
    private SizeDto size;
}
