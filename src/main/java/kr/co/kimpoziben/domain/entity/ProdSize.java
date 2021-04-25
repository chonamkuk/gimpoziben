package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@IdClass(ProdSizeId.class)
@Entity
@Table(name = "gps_prod_size_mapp")
public class ProdSize {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_seq")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "size_seq")
    private Size size;
}
