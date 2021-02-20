package kr.co.kimpoziben.test.dto;

import kr.co.kimpoziben.test.domain.entity.RpEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RpDto {
    private Long idRp;
    private Long seqRp;
    private String commentRp;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regDtRp;
    private String userRp;
    private String ynDelRp;

    public RpEntity toEntity() {
        RpEntity rpEntity = RpEntity.builder()
                .idRp(idRp)
                .seqRp(seqRp)
                .commentRp(commentRp)
                .regDtRp(regDtRp)
                .userRp(userRp)
                .ynDelRp(ynDelRp)
                .build();

        return rpEntity;
    }

    @Builder
    public RpDto(Long idRp, Long seqRp, String commentRp,
                 LocalDateTime regDtRp, String userRp, String ynDelRp ) {
        this.idRp = idRp;
        this.seqRp = seqRp;
        this.commentRp = commentRp;
        this.regDtRp = regDtRp;
        this.userRp = userRp;
        this.ynDelRp = ynDelRp;
    }
}
