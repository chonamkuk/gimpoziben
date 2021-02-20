package kr.co.kimpoziben.test.domain.repository.dsl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.kimpoziben.test.domain.entity.MeetEntity;
import kr.co.kimpoziben.domain.entity.QMeetEntity;
import kr.co.kimpoziben.domain.entity.QMeetMemberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeetRepositoryImpl implements MeetRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QMeetEntity meet = QMeetEntity.meetEntity;
    private QMeetMemberEntity meetMember = QMeetMemberEntity.meetMemberEntity;

    public MeetRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<MeetEntity> findByMember(String idAccount, String emailMember, String date) {
        return queryFactory
                .selectFrom(meet)
                .where(
                        meet.seqMeet
                        .in(
                                JPAExpressions
                                .select(meetMember.meetEntity.seqMeet)
                                .from(meetMember)
                                .where(
                                        meetMember.accountEntity.idAccount.eq(idAccount)
                                        .or(meetMember.emailMember.eq(emailMember))
                                )
                        )
                )
                .fetch();
    }
}
