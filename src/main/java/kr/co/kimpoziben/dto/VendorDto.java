package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.Product;
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
public class VendorDto {
    private Long seqVendor;
    private String nmVendor;
    private String noTelVendor;
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;

    private List<Product> productList = new ArrayList<Product>();
}
