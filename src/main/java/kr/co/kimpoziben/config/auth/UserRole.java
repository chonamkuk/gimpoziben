package kr.co.kimpoziben.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    SUPERADMIN("ROLE_SUPERADMIN", "최고 관리자");

    private final String key;
    private final String title;
}
