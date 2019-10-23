package org.activiti.core.common.spring.security.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.core.common.spring.security.LocalSpringSecurityContextPrincipalProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;


public class LocalSpringSecurityContextPrincipalProviderTest {
    
    private LocalSpringSecurityContextPrincipalProvider subject;
    
    @Before
    public void setUp() {
        subject = new LocalSpringSecurityContextPrincipalProvider();
    }

    @Test
    public void testGetCurrentPrincipalAuthenticated() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", 
                                                                                "password", 
                                                                                AuthorityUtils.createAuthorityList("ROLE_user"));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // when
        Optional<Principal> principal = subject.getCurrentPrincipal();
        
        // then
        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(principal).isNotEmpty()
                             .contains(authentication);
    }

    @Test
    public void testGetCurrentPrincipalNotAuthenticated() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", 
                                                                                "password");
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // when
        Optional<Principal> principal = subject.getCurrentPrincipal();
        
        // then
        assertThat(authentication.isAuthenticated()).isFalse();
        assertThat(principal).isEmpty();
    }
    
    @Test
    public void testGetCurrentPrincipalEmpty() {
        // given
        SecurityContextHolder.clearContext();
        
        // when
        Optional<Principal> principal = subject.getCurrentPrincipal();
        
        // then
        assertThat(principal).isEmpty();
    }
    
}
