package com.ribaso.userservice.port.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        oidcUser.getAuthorities().forEach(authority -> {
            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                Map<String, Object> claims = oidcUserAuthority.getUserInfo().getClaims();
                mappedAuthorities.addAll(extractAuthoritiesFromClaims(claims));
            }
        });

        return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractAuthoritiesFromClaims(Map<String, Object> claims) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        if (realmAccess != null) {
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            if (roles != null) {
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
            }
        }

        return authorities;
    }
}

