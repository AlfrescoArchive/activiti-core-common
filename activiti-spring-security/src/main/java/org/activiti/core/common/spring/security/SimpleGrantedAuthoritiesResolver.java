package org.activiti.core.common.spring.security;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class SimpleGrantedAuthoritiesResolver implements GrantedAuthoritiesResolver {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(@NonNull Principal principal) {
        return Optional.of(principal)
                .filter(this::isSupportedPrincipal)
                .map(this.getPrincipalClass()::cast)
                .map(this::getAuthorities)
                .orElseThrow(this::securityException);
    }
    
    protected SecurityException securityException() {
        return new SecurityException("Invalid principal authorities");
    }
    
    protected <T> Collection<? extends GrantedAuthority> getAuthorities(Authentication authentication) {
        return Optional.ofNullable(authentication.getAuthorities())
                       .orElseGet(this::emptyAuthorities);
    }
    
    protected <T> Collection<T> emptyAuthorities() {
        return Collections.emptyList();
    }
    
    protected Boolean isSupportedPrincipal(Principal principal) {
        return getPrincipalClass().isInstance(principal);
    }
    
    protected <T> Class<? extends Authentication> getPrincipalClass() {
        return Authentication.class;
    }
}