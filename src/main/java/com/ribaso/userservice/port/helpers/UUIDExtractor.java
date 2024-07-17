package com.ribaso.userservice.port.helpers;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import jakarta.annotation.Nonnull;

public class UUIDExtractor {

    /**
     * Get the UUID from an authentications principal if its of type
     * DefaultOidcUser.
     * @param authentication
     * @return
     * @throws Exception if authentication is not of type DefaultOidcUser.
     */
    public static UUID extractFromAuthentication(@Nonnull Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof DefaultOidcUser)) {
            throw new RuntimeException("Authentication not castable for: " + authentication);
        }

        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        String userID = oidcUser.getClaim("sub");
        return UUID.fromString(userID);
    }
}
