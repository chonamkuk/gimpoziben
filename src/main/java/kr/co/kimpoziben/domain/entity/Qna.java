package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "gps_qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_seq")
    private Long seqQna;

    @Column(name = "qna_title", length = 100, nullable = false)
    private String titleQna;

    @Column(name = "qna_open_yn", length = 1)
    private String ynQnaOpen;

    @Column(name = "del_yn", length = 1)
    private String ynDel;

    @Column(name = "reply_yn", length = 1)
    private String ynReply;

    @Column(name = "reply_text", length = 300)
    private String textReply;

    @Column(columnDefinition = "TEXT", name = "qna_desc")
    private String descQna;

    @Column(name = "register", length = 20, updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(name = "modifier", length = 20)
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;






}
