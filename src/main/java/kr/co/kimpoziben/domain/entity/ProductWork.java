package kr.co.kimpoziben.domain.entity;

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
@Table(name = "gps_product_work")
public class ProductWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productwork_seq")
    private Long seqWorkProduct;

    @Column(name = "productwork_title", length = 100, nullable = false)
    private String titleWorkProduct;

    @Column(name = "productwork_day", length = 20, nullable = false)
    private String dayWorkProduct;

    @Column(name = "productwork_count", nullable = false)
    private Integer countWorkProduct;

    @Column(columnDefinition = "TEXT", name = "productwork_desc")
    private String descWorkProduct;

    @Column(name = "productwork_price")
    private Integer priceWorkProduct;

    @Column(name = "productwork_type", length = 1)
    private String typeWorkProduct;

    @Column(name = "main_img_id", length = 30)
    private String idMainImg;

    @Column(name = "register", length = 20, updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(name = "modifier", length = 20)
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;


//    public void setCateList(List<ProdCate> cateList) {
//        for(ProdCate prodCate : cateList) {
//            this.addCategory(prodCate);
//        }
//    }
//
//    public void addCategory(ProdCate prodCate) {
//        prodCate.setProduct(this);
//
//        if(cateList == null) {
//            cateList = new ArrayList<>();
//        } else {
//            cateList.add(prodCate);
//        }
//    }
//
//    public void setSizeList(List<ProdSize> sizeList) {
//        for(ProdSize prodSize : sizeList) {
//            this.addSize(prodSize);
//        }
//    }



}
