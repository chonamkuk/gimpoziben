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
public class CategoryDto {
    private Long seqCategory;
    private String nmCategory;
    private Long seqUpper;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;
    private List<ProdCateDto> prodList = new ArrayList<ProdCateDto>();
}
