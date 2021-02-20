package kr.co.kimpoziben.service;

import kr.co.kimpoziben.dto.HistoryDto;
import kr.co.kimpoziben.domain.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class HistoryService {
    private HistoryRepository historyRepository;

    public void save(HistoryDto historyDto) {
        historyRepository.save(historyDto.toEntity());
    }

}
