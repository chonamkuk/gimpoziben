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

    @Column(name = "product_title", length = 100, nullable = false)
    private String titleProduct;

    @Column(name = "product_nm", length = 20, nullable = false)
    private String nmProduct;

    @Column(columnDefinition = "TEXT", name = "product_desc")
    private String descProduct;

    @Column(name = "product_color", length = 100)
    private String colorProduct;

    @Column(name = "price_buy")
    private Integer buyPrice;

    @Column(name = "price_sell")
    private Integer sellPrice;

    @Column(name = "display_yn", length = 1)
    private String ynDisplay;

    @Column(name = "soldout_yn", length = 1)
    private String ynSoldOut;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_seq")
    private Vendor vendor;

    @OneToMany(mappedBy = "seqProduct") //todo: casacade All 로 설정후 insert 되는지 확인
    private List<ProdCate> cateList = new ArrayList<ProdCate>();

    @OneToMany(mappedBy = "product")
    private List<ProdSize> sizeList = new ArrayList<ProdSize>();
}
