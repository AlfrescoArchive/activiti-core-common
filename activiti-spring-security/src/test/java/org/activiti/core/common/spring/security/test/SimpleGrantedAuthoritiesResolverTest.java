package org.activiti.core.common.spring.security.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.core.common.spring.security.SimpleGrantedAuthoritiesResolver;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.List;


public class SimpleGrantedAuthoritiesResolverTest {
    
    private SimpleGrantedAuthoritiesResolver subject = new SimpleGrantedAuthoritiesResolver();
    
    @Test
    public void testGetAuthorities() {
        // given
        // given
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("GROUP_users",
                                                                                "ROLE_admin");

        Authentication authentication = new UsernamePasswordAuthenticationToken("user", 
                                                                                "password", 
                                                                                authorities);
        
        // when
        Collection<? extends GrantedAuthority> result = subject.getAuthorities(authentication);
        
        // then
        assertThat(result).isNotEmpty()
                          .asList()
                          .containsAll(authorities);
    }

}
