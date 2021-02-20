package kr.co.kimpoziben.test.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "ij_reply")
public class RpEntity implements Serializable{

    @Column(name = "rp_id")
    private Long idRp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rp_seq")
    private Long seqRp;

    @Column(columnDefinition = "TEXT", nullable = false, name = "rp_comment")
    private String commentRp;

    @Column(nullable = true, name = "rp_regdate")
    private LocalDateTime regDtRp;

    @Column(nullable = true, name = "rp_user")
    private String userRp;

    @Column(length = 1, nullable = true, name = "rp_del_yn")
    private String ynDelRp;

    @Builder
    public RpEntity(Long idRp, Long seqRp, String commentRp, LocalDateTime regDtRp, String userRp, String ynDelRp) {
        this.idRp = idRp;
        this.seqRp = seqRp;
        this.commentRp = commentRp;
        this.regDtRp = regDtRp;
        this.userRp = userRp;
        this.ynDelRp = ynDelRp;
    }
}