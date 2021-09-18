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
@Table(name = "gps_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_seq")
    private Long seqNotice;

    @Column(name = "notice_title", length = 100, nullable = false)
    private String titleNotice;

    @Column(name = "notice_url", length = 300, nullable = false)
    private String urlNotice;

    @Column(name = "notice_start_day", length = 20, nullable = false)
    private String dayNoticeStart;

    @Column(name = "notice_end_day", length = 20, nullable = false)
    private String dayNoticeEnd;

    @Column(name = "notice_main_yn", length = 1)
    private String ynNoticeMain;

    @Column(name = "del_yn", length = 1)
    private String ynDel;

    @Column(columnDefinition = "TEXT", name = "notice_desc")
    private String descNotice;

    @Column(name = "main_img_id", length = 30)
    private String idMainImg;

    @Column(name = "register", length = 20, updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
    private LocalDateTime regDt;

    @Column(name = "modifier", length = 20)
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;






}
