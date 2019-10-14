package org.activiti.core.common.spring.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class SimpleGrantedAuthoritiesGroupsMapper extends AbstractSimpleGrantedAuthoritiesMapper 
                                                  implements GrantedAuthoritiesGroupsMapper {

    private final String prefix;
    
    public SimpleGrantedAuthoritiesGroupsMapper() {
        this("GROUP_");
    }
    
    public SimpleGrantedAuthoritiesGroupsMapper(String prefix) {
        this.prefix = prefix;
    }
    
    @Override
    public List<String> getGroups(Collection<? extends GrantedAuthority> authorities) {
        return getAuthoritesFilteredByPrefix(authorities, prefix);
    }
}    
