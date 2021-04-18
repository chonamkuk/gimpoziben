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
@Table(name = "gps_product_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_seq")
    private Long seqCategory;

    @Column(length = 20, nullable = false, name = "category_nm")
    private String nmCategory;

    @Column(name = "upper_seq")
    private Long seqUpper;

    @Column(length = 20, name = "register", updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(length = 20, name = "modifier")
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;

    @OneToMany(mappedBy = "category")
    private List<ProdCateMapp> mappList = new ArrayList<ProdCateMapp>();
}
