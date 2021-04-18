package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "gps_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_seq")
    private Long seqProduct;

    @Column(length = 20, nullable = false, name = "product_nm")
    private String nmProduct;

    @Column(columnDefinition = "TEXT", nullable = false, name = "product_desc")
    private String descProduct;

    @Column(length = 50, name = "product_size")
    private String sizeProduct;

    @Column(length = 1, nullable = false, name = "display_yn")
    private String ynDisplay;

    @Column(length = 1, nullable = false, name = "soldout_yn")
    private String ynSoldout;

    @Column(length = 30, name = "main_img_id")
    private String idMainImg;

    @Column(length = 30, name = "desc_img_id")
    private String idDescImg;

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

    @OneToMany(mappedBy = "product")
    private List<ProdCateMapp> mappList = new ArrayList<ProdCateMapp>();
}
