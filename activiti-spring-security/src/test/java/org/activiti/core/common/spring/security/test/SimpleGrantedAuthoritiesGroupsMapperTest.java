package org.activiti.core.common.spring.security.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.core.common.spring.security.SimpleGrantedAuthoritiesGroupsMapper;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;


public class SimpleGrantedAuthoritiesGroupsMapperTest {
    
    private SimpleGrantedAuthoritiesGroupsMapper subject = new SimpleGrantedAuthoritiesGroupsMapper();  

    @Test
    public void testGetGroups() {
        // given
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("GROUP_users,ROLE_admin");
        
        // when
        List<String> result = subject.getGroups(authorities);
        
        // then
        assertThat(result).isNotEmpty()
                          .containsExactly("users");
    }

}
