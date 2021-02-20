package kr.co.kimpoziben.domain.entity;

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
@Table(name = "ij_history")
public class HistoryEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_seq")
    private int seqHist;

    @Column(nullable = true, name = "history_regdate")
    private LocalDateTime regHist;

    @Column(nullable = true, name = "history_user")
    private String userHist;

    @Column(nullable = true, name = "history_url")
    private String urlHist;

    @Column(nullable = true, name = "history_ip")
    private String ipHist;

    @Column(nullable = true, name = "history_param")
    private String ParamHist;

    @Builder
    public HistoryEntity(int seqHist, LocalDateTime regHist, String userHist, String urlHist, String ipHist, String ParamHist) {
        this.seqHist = seqHist;
        this.regHist = regHist;
        this.userHist = userHist;
        this.urlHist = urlHist;
        this.ipHist = ipHist;
        this.ParamHist = ParamHist;
    }

}