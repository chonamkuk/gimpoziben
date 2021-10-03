package kr.co.kimpoziben.config.auth;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String userRole;
    private String nmCompany;
    private String addrCompany;
    private String noCompany;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.userRole = user.getUserRole().name();
        this.nmCompany = user.getNmCompany();
        this.addrCompany = user.getAddrCompany();
        this.noCompany = user.getNoCompany();
    }
}
