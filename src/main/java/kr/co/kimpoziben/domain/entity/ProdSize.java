package kr.co.kimpoziben.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@IdClass(ProdSizeId.class)
@Entity
@Table(name = "gps_prod_size_mapp")
public class ProdSize {

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "product_seq")
//    private Product product;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "size_seq")
//    private Size size;
//
//    @Builder
//    public ProdSize(Product product, Size size) {
//        this.product = product;
//        this.size = size;
//    }

    @Id
    private Long seqProduct;
    @Id
    private Long seqSize;

    @Builder
    public ProdSize(Long seqProduct, Long seqSize) {
        this.seqProduct = seqProduct;
        this.seqSize = seqSize;
    }
}
