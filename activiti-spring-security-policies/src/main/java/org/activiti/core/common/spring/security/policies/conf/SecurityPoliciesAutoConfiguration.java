package org.activiti.core.common.spring.security.policies.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SecurityPoliciesProperties.class)
@ComponentScan("org.activiti.core.common.spring.security.policies")
public class SecurityPoliciesAutoConfiguration {

}
