package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@IdClass(ProdCateId.class)
@Table(name = "gps_prod_cate_mapp")
public class ProdCate {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_seq")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_seq")
    private Category category;
}