package com.cos.security.config.auth;

import com.cos.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행
 * 로그인을 진행이 완료가 되면 시큐리티 session 생성 (Security ContextHolder)
 * 오브젝트 => Authentication 타입 객체
 * User 오브젝트타입 => UserDetails 타입 객체
 **/

// Security Session => Authentication => UserDetails(PrincipalDetails)
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user;  // 콤포지션

    // 해당 User의 권한을 리턴하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) user::getRole);
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 휴면 계정 여부
        // 예시 - 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        // 현재시간 - 로그인 시간 => 1년을 초과하면 return false
        return true;
    }
}
