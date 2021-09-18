package kr.co.kimpoziben.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "gps_size")
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_seq")
    private Long seqSize;

    @Column(length = 20, nullable = false, name = "size_nm")
    private String nmSize;

    @Column(name = "size_ordr")
    private Integer ordrSize;

    @OneToMany(mappedBy = "upperSize", fetch = FetchType.LAZY)
    @OrderBy("ordrSize asc")
    private List<Size> subSizeList;

    @ManyToOne
    @JoinColumn(name = "upper_seq")
    private Size upperSize;

    @Column(length = 20, name = "register", updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(length = 20, name = "modifier")
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;

    public void addSubSize(Size subSize) {
        if(subSizeList == null) {
            subSizeList = new ArrayList<Size>();
        }

        subSizeList.add(subSize);
    }
}
