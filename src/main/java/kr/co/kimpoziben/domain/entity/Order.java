package kr.co.kimpoziben.domain.entity;

import kr.co.kimpoziben.domain.code.OrderStat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "gps_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_seq")
    private Long seqOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_stat")
    private OrderStat statOrder;

    @Column(name = "customer_id", updatable = false)
    private Long idCustomer;

    @Column(name = "order_dt", updatable = false)
    private LocalDateTime dtOrder;

    @Column(name = "modifier", length = 20)
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;

    @Column(name = "order_price_total")
    private Long totalPriceOrder;

    @Column(name = "excel_file_path")
    private String pathFileExcel;

    @OneToMany(mappedBy = "seqOrder")
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

    @Override
    public String toString() {
        return "Order{" +
                "seqOrder=" + seqOrder +
                ", statOrder=" + statOrder +
                ", idCustomer='" + idCustomer + '\'' +
                ", dtOrder=" + dtOrder +
                ", modifier='" + modifier + '\'' +
                ", modDt=" + modDt +
                ", totalPriceOrder=" + totalPriceOrder +
                ", pathFileExcel=" + pathFileExcel +
                ", orderProductList=" + orderProductList +
                '}';
    }
}
