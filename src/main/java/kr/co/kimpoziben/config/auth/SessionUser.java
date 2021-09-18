package kr.co.kimpoziben.config.auth;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String mobile;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
    }
}
