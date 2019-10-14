package org.activiti.core.common.spring.security;

import org.activiti.api.runtime.shared.security.PrincipalIdentityProvider;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.Optional;

public class AuthenticationPrincipalIdentityProvider implements PrincipalIdentityProvider {

    private final static String EMPTY_ANONYMOUS_USER_ID = "";
    
    @Override
    public String getUserId(Principal principal) {
        return Optional.of(principal)
                       .filter(Authentication.class::isInstance)
                       .map(Authentication.class::cast)
                       .map(this::getUserId)
                       .orElseThrow(this::securityException);
    }
    
    protected String getUserId(Authentication authentication) {
        return Optional.ofNullable(authentication.getName())
                       .orElseGet(this::getAnonymousUserId);
    }
    
    protected String getAnonymousUserId() {
        return EMPTY_ANONYMOUS_USER_ID;
    }
    
    protected SecurityException securityException() {
        return new SecurityException("Invalid principal authentication object instance");
    }
}