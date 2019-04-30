/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.core.common.spring.connector.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.core.common.spring.connector.ConnectorDefinitionReader;
import org.activiti.core.common.spring.connector.ConnectorResourceFinderDescriptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.activiti.core.common.spring.connector")
public class ConnectorAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass(value = "org.springframework.http.converter.json.Jackson2ObjectMapperBuilder")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ConnectorDefinitionReader connectorReader(ObjectMapper objectMapper){
        return new ConnectorDefinitionReader(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectorResourceFinderDescriptor connectorResourceFinderDescriptor(@Value("${spring.activiti.checkProcessDefinitions:true}") boolean lookUpResources,
                                                                               @Value("${activiti.connectors.dir:classpath:/connectors/}") String connectorRoot,
                                                                               ConnectorDefinitionReader connectorReader) {
        if (connectorRoot == null) {
            throw new IllegalArgumentException("'activiti.connectors.dir' cannot be null");
        }
        if (!connectorRoot.contains("connectors")) {
            throw new IllegalArgumentException("'activiti.connectors.dir' should contain 'connectors' as a substring. Current value is `" + connectorRoot);
        }
        return new ConnectorResourceFinderDescriptor(lookUpResources,
                                                     connectorRoot,
                                                     connectorReader);
    }

}
