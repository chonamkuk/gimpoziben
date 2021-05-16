package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SizeDto {
    private Long seqSize;
    private String nmSize;
    private Integer ordrSize;
    private List<SizeDto> subSizeList;
    private SizeDto upperSize;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;
}
