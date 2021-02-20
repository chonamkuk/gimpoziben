package kr.co.kimpoziben.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(length = 100, nullable = false, name = "account_id")
    private String idAccount;

    @Column(length = 1000, nullable = false, name = "account_password")
    private String passwordAccount;

    @Column(length = 100, name = "account_name")
    private String nameAccount;

    @Column(length = 100, name = "account_email")
    private String emailAccount;

    @Column(length = 10, name = "account_role")
    private String roleAccount;

    @Builder
    public AccountEntity(String idAccount, String passwordAccount, String nameAccount, String emailAccount, String roleAccount){
        this.idAccount = idAccount;
        this.passwordAccount = passwordAccount;
        this.nameAccount = nameAccount;
        this.emailAccount = emailAccount;
        this.roleAccount = roleAccount;
    }
}
