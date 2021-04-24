package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@IdClass(ProdSizeId.class)
@Entity
@Table(name = "gps_prod_size_mapp")
public class ProdSizeMapp {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_seq")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "size_seq")
    private Size size;
}
