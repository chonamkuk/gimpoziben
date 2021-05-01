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
@Table(name = "gps_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_seq")
    private Long seqProduct;

    @Column(length = 100, nullable = false, name = "product_title")
    private String titleProduct;

    @Column(length = 20, nullable = false, name = "product_nm")
    private String nmProduct;

    @Column(columnDefinition = "TEXT", name = "product_desc")
    private String descProduct;

    @Column(length = 100, name = "product_color")
    private String colorProduct;

    @Column(name = "price_buy")
    private int buyPrice;

    @Column(name = "price_sell")
    private int sellPrice;

    @Column(length = 1, nullable = false, name = "display_yn")
    private String ynDisplay;

    @Column(length = 1, nullable = false, name = "soldout_yn")
    private String ynSoldout;

    @Column(length = 30, name = "main_img_id")
    private String idMainImg;

    @Column(length = 20, name = "register", updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(length = 20, name = "modifier")
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;

    @ManyToOne
    @JoinColumn(name = "vendor_seq")
    private Vendor vendor;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProdCate> cateList = new ArrayList<ProdCate>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProdSize> sizeList = new ArrayList<ProdSize>();

    public void setCateList(List<ProdCate> cateList) {
        for(ProdCate prodCate : cateList) {
            this.addCategory(prodCate);
        }
    }

    public void addCategory(ProdCate prodCate) {
        prodCate.setProduct(this);

        if(cateList == null) {
            cateList = new ArrayList<>();
        } else {
            cateList.add(prodCate);
        }
    }

    public void setSizeList(List<ProdSize> sizeList) {
        for(ProdSize prodSize : sizeList) {
            this.addSize(prodSize);
        }
    }

    public void addSize(ProdSize prodSize) {
        prodSize.setProduct(this);

        if(sizeList == null) {
            sizeList = new ArrayList<>();
        } else {
            sizeList.add(prodSize);
        }
    }

}
