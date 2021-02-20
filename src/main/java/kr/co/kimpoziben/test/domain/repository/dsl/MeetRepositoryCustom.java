package kr.co.kimpoziben.test.domain.repository.dsl;

import kr.co.kimpoziben.test.domain.entity.MeetEntity;

import java.util.List;

public interface MeetRepositoryCustom {
    List<MeetEntity> findByMember(String idAccount, String emailMember, String date);
}
