package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.HistoryEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HistoryDto {
    private int seqHist;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regHist;
    private String userHist;
    private String urlHist;
    private String ipHist;
    private String ParamHist;

    public HistoryEntity toEntity() {
        HistoryEntity historyEntity = HistoryEntity.builder()
                .seqHist(seqHist)
                .regHist(regHist)
                .userHist(userHist)
                .urlHist(urlHist)
                .ipHist(ipHist)
                .ParamHist(ParamHist)
                .build();

        return historyEntity;
    }

    @Builder
    public HistoryDto(int seqHist, LocalDateTime regHist, String userHist, String urlHist, String ipHist, String ParamHist ) {
        this.seqHist = seqHist;
        this.regHist = regHist;
        this.userHist = userHist;
        this.urlHist = urlHist;
        this.ipHist = ipHist;
        this.ParamHist = ParamHist;
    }
}
