/*
 * Copyright 2019 Alfresco, Inc. and/or its affiliates.
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

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.core.common.model.connector.ConnectorDefinition;
import org.activiti.spring.resources.ResourceReader;

public class ConnectorDefinitionReader implements ResourceReader<ConnectorDefinition> {

    private ObjectMapper objectMapper;

    public ConnectorDefinitionReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Predicate<String> getResourceNameSelector() {
        Predicate<String> connectorsPredicate = resourceFileName -> resourceFileName.contains("connectors");
        return connectorsPredicate.and(resourceFileName -> resourceFileName.endsWith(".json"));
    }

    @Override
    public ConnectorDefinition read(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream,
                                      ConnectorDefinition.class);
    }
}
