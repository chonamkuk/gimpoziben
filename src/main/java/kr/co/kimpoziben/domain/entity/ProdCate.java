package kr.co.kimpoziben.domain.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@IdClass(ProdCateId.class)
@Table(name = "gps_prod_cate_mapp")
public class ProdCate {

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "product_seq")
//    private Product product;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "category_seq")
//    private Category category;
//
//    @Builder
//    public ProdCate(Product product, Category category){
//        this.product = product;
//        this.category = category;
//    }

    @Id
    private Long seqProduct;
    @Id
    private Long seqCategory;

    @Builder
    public ProdCate(Long seqProduct, Long seqCategory) {
        this.seqProduct = seqProduct;
        this.seqCategory = seqCategory;
    }
}
