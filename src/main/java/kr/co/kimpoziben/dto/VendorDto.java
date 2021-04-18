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
public class VendorDto {
    private Long seqVendor;
    private String nmVendor;
    private String noTelVendor;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;
}
