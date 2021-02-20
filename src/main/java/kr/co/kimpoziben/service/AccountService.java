package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.AccountEntity;
import kr.co.kimpoziben.domain.repository.AccountRepository;
import kr.co.kimpoziben.dto.AccountDto;
import kr.co.kimpoziben.dto.SearchDto;
import kr.co.kimpoziben.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private AccountRepository accountRepository;

    public String save(AccountDto accountDto) {
        return accountRepository.save(accountDto.toEntity()).getIdAccount();
    }

    @Override
    public UserDetails loadUserByUsername(String idAccount) throws UsernameNotFoundException {
        AccountEntity accountEntity = accountRepository.findByIdAccount(idAccount).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        AccountDto accountDto = AccountDto.builder()
                .idAccount(accountEntity.getIdAccount())
                .passwordAccount(accountEntity.getPasswordAccount())
                .nameAccount(accountEntity.getNameAccount())
                .emailAccount(accountEntity.getEmailAccount())
                .roleAccount(accountEntity.getRoleAccount())
                .build();

        return accountDto;
    }

    public HashMap getList(Pageable pageable, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        Page<AccountEntity> entityPage = null;
        entityPage = accountRepository.findAll((Specification<AccountEntity>) SearchSpec.searchLike3(searchDto), pageable);

        List<AccountEntity> entities = entityPage.getContent();
        List<AccountDto> dtoList = new ArrayList<>();
        for(AccountEntity accountEntity : entities) {
            AccountDto tempDto = AccountDto.builder()
                    .idAccount(accountEntity.getIdAccount())
                    .emailAccount(accountEntity.getEmailAccount())
                    .nameAccount(accountEntity.getNameAccount())
                    .roleAccount(accountEntity.getRoleAccount())
                    .build();

            dtoList.add(tempDto);
        }

        result.put("pageAble", entityPage.getPageable());
        result.put("hasNext", entityPage.hasNext());
        result.put("dtoList", dtoList);

        return result;
    }
}
