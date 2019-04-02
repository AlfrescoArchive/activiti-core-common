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

package org.activiti.core.common.spring.connector;

import org.activiti.core.common.spring.connector.autoconfigure.ConnectorAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectorAutoConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(locations = "classpath:application-duplicate-test.properties")
public class DuplicateConnectorDefinitionServiceIT {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ResourcePatternResolver resourceLoader;
	
    /**
     * Two files have duplicated name.
     **/
    @Test
    public void DuplicateConnectorDefinitions() throws IOException {
    	
    	ConnectorDefinitionService connectorDefinitionService = new ConnectorDefinitionService(
    			"classpath:/duplicate_connectors/",
    			objectMapper, 
    			resourceLoader);
        
        Throwable throwable = catchThrowable(() -> connectorDefinitionService.get());
        
        //then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class);
        
    }
    
}
