package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.code.OrderStat;
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
public class OrderDto {
    private Long seqOrder;
    private OrderStat statOrder;
    private String idCustomer;
    private LocalDateTime dtOrder;
    private Long totalPriceOrder;
    private String pathFileExcel;
    private List<OrderProductDto> products = new ArrayList<OrderProductDto>();
}
