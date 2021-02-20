package kr.co.kimpoziben.test.service;

import kr.co.kimpoziben.test.domain.code.MeetStat;
import kr.co.kimpoziben.domain.entity.AccountEntity;
import kr.co.kimpoziben.test.domain.entity.MeetEntity;
import kr.co.kimpoziben.test.domain.entity.MeetMemberEntity;
import kr.co.kimpoziben.test.domain.repository.MeetRepository;
import kr.co.kimpoziben.dto.*;
import kr.co.kimpoziben.test.dto.MeetDto;
import kr.co.kimpoziben.test.dto.MeetMemberDto;
import kr.co.kimpoziben.util.AES256Util;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class MeetService {
    @Autowired
    AES256Util aes;

    @Autowired
    private MeetRepository meetRepository;

    ModelMapper modelMapper = null;

    private MeetService(){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.typeMap(MeetDto.class, MeetEntity.class)
                .addMapping(MeetDto::getMeetMembers, MeetEntity::setMeetMembers);
        modelMapper.typeMap(MeetMemberDto.class, MeetMemberEntity.class)
                .addMapping(MeetMemberDto::getAccountDto, MeetMemberEntity::setAccountEntity);
        modelMapper.typeMap(AccountDto.class, AccountEntity.class)
                .addMapping(AccountDto::getIdAccount, AccountEntity::setIdAccount);
    }

    @Transactional
    public Long save(MeetDto meetDto) {
        meetDto.setStatMeet(MeetStat.A);
        meetDto.setYnDel("N");
        meetDto.setRegDt(LocalDateTime.now());
        MeetEntity meetEntity = modelMapper.map(meetDto, MeetEntity.class);

        Long seqMeet = meetRepository.save(meetEntity).getSeqMeet();

        /**
         * todo 맵핑관계 정리
         * 1. 방안A
         * meet에 있는 meetmember 리스트를 일대다, 연관관계의 주인이 아닌 것으로 변경 mappedBy = "meet"
         * meetmember 에 있는 meet를 다대일, 연관관계의 주인으로 설정
         * meetmember에서 setMeet 메소드에 아래와 같은 로직 추가
         *         if(this.team != null) {
         *             this.team.getMembers().remove(this); // update 할때
         *         }
         *
         *         this.team = team;
         *         team.getMembers().add(this); // insert 할때
         *
         * 2. 방안B
         * manytomany 사용
         * JoinTable로 meeting_member 를 지정
         * 계정이 없는 경우도 있으므로 meet - account 가 항상 맵핑되지 않는다 -> 방안B는 계정없는 참여자 구현이 어려움
         *
         * 3. 방안C
         * manytomany 새로운 기본키 사용 231 페이지
         */

        return seqMeet;
    }

    public MeetDto getDetail(Long seqMeet) throws Exception {
        MeetEntity meetEntity = meetRepository.getOne(seqMeet);
        MeetDto meetDto = modelMapper.map(meetEntity, MeetDto.class);

        return meetDto;
    }

    public List<MeetDto> getList(AccountDto accountSession, SearchDto searchDto) throws Exception {
//        List<MeetEntity> meetEntityList = meetRepository.findByMember(accountSession.getIdAccount(),"","");
        System.out.println("asdasd");
        List<MeetEntity> meetEntityList = meetRepository.findByMember("gogildong","","");
        List<MeetDto> meetDtoList = new ArrayList<>();
        //todo: 스트림API로 개선
        for(MeetEntity meetEntity : meetEntityList) {
            meetDtoList.add(modelMapper.map(meetEntity, MeetDto.class));
        }

        return meetDtoList;
    }

    public MeetDto update(MeetDto meetDto) throws Exception {

        //todo: contoller에서 넘어온 meetdto를 db와 비교해서 update
        //todo: modelMapper를 서비스 단위에서 활용할 수 있도록 생성자에 추가
        MeetEntity meetEntity = meetRepository.getOne(meetDto.getSeqMeet());
        return modelMapper.map(meetEntity, MeetDto.class);
    }
}
