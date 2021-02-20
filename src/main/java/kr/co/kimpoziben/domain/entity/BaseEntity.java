package kr.co.kimpoziben.domain.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    //todo: LocalDateTime 맵핑 방안...@Temporal 지정?
    private String register;
    private LocalDateTime regDt;
    private String modifier;
    private LocalDateTime modDt;
}
