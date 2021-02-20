package kr.co.kimpoziben.test.domain.entity;

import kr.co.kimpoziben.domain.entity.AccountEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "meeting_member")
public class MeetMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seqMember;

    @ManyToOne
    @JoinColumn(name = "meet_seq")
    private MeetEntity meetEntity;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @Column(length = 100, name = "member_name")
    private String nameMember;

    @Column(length = 100, name ="member_email")
    private String emailMember;

    @Builder
    public MeetMemberEntity(Long seqMember, MeetEntity meetEntity, AccountEntity accountEntity, String nameMember, String emailMember) {
        this.seqMember = seqMember;
        this.meetEntity = meetEntity;
        this.accountEntity = accountEntity;
        this.nameMember = nameMember;
        this.emailMember = emailMember;
    }
}
