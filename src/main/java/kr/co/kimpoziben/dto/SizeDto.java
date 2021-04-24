package kr.co.kimpoziben.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SizeDto {
    private Long seqSize;
    private String nmSize;
    private Long seqUpper;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;
}
