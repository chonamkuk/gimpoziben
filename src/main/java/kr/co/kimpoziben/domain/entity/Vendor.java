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
@Table(name = "gps_vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_seq")
    private Long seqVendor;

    @Column(length = 50,  nullable = false, name = "vendor_nm")
    private String nmVendor;

    @Column(length = 100, name = "vendor_tel_no")
    private String noTelVendor;

    @Column(length = 20, name = "register", updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(length = 20, name = "modifier")
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;

    @OneToMany(mappedBy = "vendor")
    private List<Product> productList = new ArrayList<Product>();
}
