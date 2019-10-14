package org.activiti.core.common.spring.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class SimpleGrantedAuthoritiesRolesMapper extends AbstractSimpleGrantedAuthoritiesMapper 
                                                 implements GrantedAuthoritiesRolesMapper {
    private final String prefix;
    
    public SimpleGrantedAuthoritiesRolesMapper() {
        this("ROLE_");
    }
    
    public SimpleGrantedAuthoritiesRolesMapper(String prefix) {
        this.prefix = prefix;
    }
    
    @Override
    public List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        return getAuthoritesFilteredByPrefix(authorities, prefix);
    }
}