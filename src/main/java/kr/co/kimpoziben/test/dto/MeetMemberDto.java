package kr.co.kimpoziben.test.dto;

import kr.co.kimpoziben.dto.AccountDto;
import kr.co.kimpoziben.test.domain.entity.MeetMemberEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeetMemberDto {

    private Long seqMember;
    private MeetDto meetDto;
    private AccountDto accountDto;
    private String nameMember;
    private String emailMember;

    public MeetMemberEntity toEntity() {
        MeetMemberEntity meetMemberEntity = MeetMemberEntity.builder()
                .seqMember(seqMember)
                .meetEntity(meetDto.toEntity())
                .accountEntity(accountDto.toEntity())
                .nameMember(nameMember)
                .emailMember(emailMember)
                .build();

        return meetMemberEntity;
    }

    @Builder
    public MeetMemberDto(Long seqMember, MeetDto meetDto, AccountDto accountDto, String nameMember, String emailMember) {
        this.seqMember = seqMember;
        this.meetDto = meetDto;
        this.accountDto = accountDto;
        this.nameMember = nameMember;
        this.emailMember = emailMember;
    }
}
