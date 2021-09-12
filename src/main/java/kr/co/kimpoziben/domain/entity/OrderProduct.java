package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "gps_order_product")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_producet_seq")
    private Long seqProductOrder;

    @Column(name = "order_seq")
    private Long seqOrder;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "product_seq")
    private Long seqProduct;

    @Column(name = "size_seq")
    private Long seqSize;

    @Column(name = "main_img_id")
    private String idMainImg;

    @Column(name = "order_amount")
    private Long amountOrder;

    @Column(name = "order_price")
    private Long priceOrder;

    @Override
    public String toString() {
        return "OrderProduct{" +
                "seqProductOrder=" + seqProductOrder +
                ", seqOrder=" + seqOrder +
                ", cartId=" + cartId +
                ", seqProduct=" + seqProduct +
                ", seqSize=" + seqSize +
                ", idMainImg='" + idMainImg + '\'' +
                ", amountOrder=" + amountOrder +
                ", priceOrder=" + priceOrder +
                '}';
    }
}
