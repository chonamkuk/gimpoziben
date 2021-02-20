package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.AccountEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity> {
    Optional<AccountEntity> findByIdAccount(String idAccount);

    List<AccountEntity> findByNameAccountLike(Pageable pageable, String nameAccount);
}
