package kr.co.kimpoziben.config.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "gps_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_nm", nullable = false)
    private String name;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_no_mobile")
    private String mobile;

    @Column(name = "user_no_phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Column(name = "company_nm")
    private String nmCompany;

    @Column(name = "company_addr")
    private String addrCompany;

    @Column(name = "company_no")
    private String noCompany;

    @Builder
    public User(String name, String email, String mobile, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.userRole = userRole;
    }

    public User update(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;

        return this;
    }

    public String getUserRoleKey() {
        return this.userRole.getKey();
    }

}
