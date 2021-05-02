package kr.co.kimpoziben.domain.entity;

import javax.persistence.Column;
import java.io.Serializable;

public class ProdCateId implements Serializable {
//    private Product product;
//    private Category category;
    @Column(name = "product_seq")
    private Long seqProduct;

    @Column(name = "category_seq")
    private Long seqCategory;
}
