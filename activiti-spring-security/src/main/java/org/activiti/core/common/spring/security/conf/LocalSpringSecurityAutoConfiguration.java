package org.activiti.core.common.spring.security.conf;

import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalSpringSecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SecurityManager.class)
    public LocalSpringSecurityManager springSecurityManager(){
        return new LocalSpringSecurityManager();
    }
}
