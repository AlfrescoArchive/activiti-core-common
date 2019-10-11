package org.activiti.core.common.spring.security;

import org.activiti.api.runtime.shared.security.SecurityManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * This is a simple wrapper for Spring Security Context Holder
 */
public class LocalSpringSecurityManager implements SecurityManager {

    @Override
    public String getAuthenticatedUserId() {
        return getCurrentUserAuthentication().map(Authentication::getName)
                                             .orElse("");
    }

    @Override
    public Collection<String> getAuthenticatedUserGroups() {
        return getCurrentUserGrantedAuthorities().map(this::getGroups)
                                                 .orElseThrow(() -> new SecurityException("Invalid groups user security context"));
    }

    @Override
    public Collection<String> getAuthenticatedUserRoles() {
        return getCurrentUserGrantedAuthorities().map(this::getRoles)
                                                 .orElseThrow(() -> new SecurityException("Invalid roles user security security context"));
    }
    
    private List<String> getGroups(Collection<? extends GrantedAuthority> authorities) {
        return getAuthoritesByPrefix(authorities, "GROUP_");
    }

    private List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        return getAuthoritesByPrefix(authorities, "ROLE_");
    }

    private List<String> getAuthoritesByPrefix(Collection<? extends GrantedAuthority> authorities,
                                               String prefix) {
        return authorities.stream()
                          .map(GrantedAuthority::getAuthority)
                          .filter(a -> a.startsWith(prefix))
                          .map(a -> a.substring(prefix.length()))
                          .collect(Collectors.collectingAndThen(Collectors.toList(),
                                                                Collections::unmodifiableList));
    }
    
    private Optional<Collection<? extends GrantedAuthority>> getCurrentUserGrantedAuthorities() {
        return getCurrentUserAuthentication().map(Authentication::getAuthorities);                
    }
    
    private Optional<Authentication> getCurrentUserAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                       .map(SecurityContext::getAuthentication);
    }
    
}
