package kr.co.kimpoziben.domain.entity;

import javax.persistence.Column;
import java.io.Serializable;

public class ProdSizeId implements Serializable {
//    private Product product;
//    private Size size;

    @Column(name = "product_seq")
    private Long seqProduct;
    @Column(name = "size_seq")
    private Long seqSize;
}
