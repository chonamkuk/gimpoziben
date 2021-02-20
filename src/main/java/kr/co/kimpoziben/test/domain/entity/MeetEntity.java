package kr.co.kimpoziben.test.domain.entity;

import kr.co.kimpoziben.test.domain.code.MeetStat;
import kr.co.kimpoziben.test.domain.code.MeetType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "meeting")
public class MeetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meet_seq")
    private Long seqMeet;

    @Column(length = 100, name = "meet_title")
    private String titleMeet;

    @Column(name = "meet_date")
    private LocalDate dateMeet;

    @Column(name = "meet_start_time")
    private LocalTime timeStart;

    @Column(name = "meet_end_time")
    private LocalTime timeEnd;

    @Column(name = "meet_latitude")
    private BigDecimal latMeet;

    @Column(name = "meet_longitude")
    private BigDecimal lonMeet;

    @Column(name = "meet_location")
    private String locationMeet;

    @Column(name = "meet_location_detail")
    private String dtlLocationMeet;

    @Column(length = 100, name = "meet_password")
    private String passwordMeet;

    @Column(name = "meet_prev")
    private Long prevMeet;

    @Column(length = 1, name = "meet_type")
    @Enumerated(EnumType.STRING)
    private MeetType typeMeet;

    @Column(length = 1, name = "meet_stat")
    @Enumerated(EnumType.STRING)
    private MeetStat statMeet;

    @Column(length = 20, name = "register", updatable = false)
    private String register;

    @Column(name = "regdt", updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime regDt;

    @Column(length = 20, name = "modifier")
    private String modifier;

    @Column(name = "moddt")
    private LocalDateTime modDt;

    @Column(length = 1, name = "del_yn")
    private String ynDel;

    @OneToMany(mappedBy = "meetEntity", cascade = CascadeType.ALL)
    private List<MeetMemberEntity> meetMembers = new ArrayList<MeetMemberEntity>();

    @Builder
    public MeetEntity(Long seqMeet, String titleMeet, LocalDate dateMeet, LocalTime timeStart, LocalTime timeEnd,
                      BigDecimal latMeet, BigDecimal lonMeet, String locationMeet, String dtlLocationMeet, String passwordMeet,
                      Long prevMeet, MeetType typeMeet, MeetStat statMeet, String register, LocalDateTime regDt, String modifier,
                      LocalDateTime modDt, String ynDel, List meetMembers) {
        this.seqMeet = seqMeet;
        this.titleMeet = titleMeet;
        this.dateMeet = dateMeet;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.latMeet = latMeet;
        this.lonMeet = lonMeet;
        this.locationMeet = locationMeet;
        this.dtlLocationMeet = dtlLocationMeet;
        this.passwordMeet = passwordMeet;
        this.prevMeet = prevMeet;
        this.typeMeet = typeMeet;
        this.statMeet = statMeet;
        this.register = register;
        this.regDt = regDt;
        this.modifier = modifier;
        this.modDt = modDt;
        this.ynDel = ynDel;
        this.meetMembers = meetMembers;
    }

    public void setMeetMembers(List<MeetMemberEntity> meetMembers){
        for(MeetMemberEntity meetMemberEntity : meetMembers) {
            this.addMeetMember(meetMemberEntity);
        }
    }

    public void addMeetMember(MeetMemberEntity meetMemberEntity) {
//        MeetMemberEntity meetMemberEntity = new MeetMemberEntity();
//        meetMemberEntity.setAccountEntity(accountEntity);
//        meetMemberEntity.setNameMember(nameMember);
//        meetMemberEntity.setEmailMember(emailMember);
        System.out.println(meetMemberEntity.getAccountEntity().getIdAccount());
        meetMemberEntity.setMeetEntity(this);

        if(meetMembers == null) {
            meetMembers = new ArrayList<>();
        } else {
            meetMembers.add(meetMemberEntity);
        }
    }
}
