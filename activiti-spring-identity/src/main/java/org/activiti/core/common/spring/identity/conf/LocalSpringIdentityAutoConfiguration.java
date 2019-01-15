package org.activiti.core.common.spring.identity.conf;

import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

public class LocalSpringIdentityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService(){
        return new ExtendedInMemoryUserDetailsManager();
    }

    @Bean
    @ConditionalOnMissingBean(UserGroupManager.class)
    public UserGroupManager UserGroupManager(UserDetailsService userDetailsService){
        return new ActivitiUserGroupManagerImpl(userDetailsService);
    }
}
