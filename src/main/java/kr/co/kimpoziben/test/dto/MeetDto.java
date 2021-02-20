package kr.co.kimpoziben.test.dto;

import kr.co.kimpoziben.test.domain.code.MeetStat;
import kr.co.kimpoziben.test.domain.code.MeetType;
import kr.co.kimpoziben.test.domain.entity.MeetEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@ToString
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class MeetDto {

    private Long seqMeet;
    private String titleMeet;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateMeet;
//    @DateTimeFormat(pattern = "hh:mm", iso = DateTimeFormat.ISO.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeStart;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeEnd;
    private BigDecimal latMeet;
    private BigDecimal lonMeet;
    private String locationMeet;
    private String dtlLocationMeet;
    private String passwordMeet;
    private Long prevMeet;
    private MeetType typeMeet;
    private MeetStat statMeet;
    private String register;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regDt;
    private String modifier;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modDt;
    private String ynDel;
    private List<MeetMemberDto> meetMembers = new ArrayList<>();

    public MeetEntity toEntity() {
        MeetEntity meetEntity = MeetEntity.builder()
                .seqMeet(seqMeet)
                .titleMeet(titleMeet)
                .dateMeet(dateMeet)
                .timeStart(timeStart)
                .timeEnd(timeEnd)
                .latMeet(latMeet)
                .lonMeet(lonMeet)
                .locationMeet(locationMeet)
                .dtlLocationMeet(dtlLocationMeet)
                .passwordMeet(passwordMeet)
                .prevMeet(prevMeet)
                .typeMeet(typeMeet)
                .statMeet(statMeet)
                .register(register)
                .regDt(regDt)
                .modifier(modifier)
                .modDt(modDt)
                .ynDel(ynDel)
                .meetMembers(meetMembers)
                .build();

        return meetEntity;
    }

    @Builder
    public MeetDto(Long seqMeet, String titleMeet, LocalDate dateMeet, LocalTime timeStart, LocalTime timeEnd,
                      BigDecimal latMeet, BigDecimal lonMeet, String locationMeet, String dtlLocationMeet, String passwordMeet,
                      Long prevMeet, MeetType typeMeet, MeetStat statMeet, String register, LocalDateTime regDt, String modifier,
                      LocalDateTime modDt, String ynDel, List<MeetMemberDto> meetMembers) {
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

//    public void addMeetMember(MeetMemberDto meetMemberDto) {
//        if(this.meetMembers == null) {
//            meetMembers = new ArrayList<MeetMemberDto>();
//        }
//        meetMembers.add(meetMemberDto);
//    }

    public void setMeetMembers(List<MeetMemberDto> meetMembers) {
        for(MeetMemberDto meetMemberDto : meetMembers) {
            meetMemberDto.setMeetDto(this);
        }
        this.meetMembers = meetMembers;
    }

}
