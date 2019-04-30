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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.core.common.model.connector.ConnectorDefinition;
import org.activiti.spring.resources.ResourceFinderDescriptor;
import org.springframework.core.io.Resource;

public class ConnectorResourceFinderDescriptor implements ResourceFinderDescriptor {

    private static List<String> CONNECTOR_SUFFIXES = Collections.singletonList("**.json");

    private final boolean lookUpResources;
    private final String locationPrefix;
    private final ConnectorDefinitionReader connectorReader;

    public ConnectorResourceFinderDescriptor(boolean lookUpResources,
                                             String locationPrefix,
                                             ConnectorDefinitionReader connectorReader) {

        this.lookUpResources = lookUpResources;
        this.locationPrefix = locationPrefix;
        this.connectorReader = connectorReader;
    }

    @Override
    public List<String> getLocationSuffixes() {
        return CONNECTOR_SUFFIXES;
    }

    @Override
    public String getLocationPrefix() {
        return locationPrefix;
    }

    @Override
    public boolean shouldLookUpResources() {
        return lookUpResources;
    }

    @Override
    public String getMsgForEmptyResources() {
        return "No connectors were found for auto-deployment in the location '" + getLocationPrefix() + "'";
    }

    @Override
    public String getMsgForResourcesFound(List<String> connectors) {
        return "The following connector files will be deployed: " + connectors;
    }

    @Override
    public void validate(List<Resource> resources) throws IOException {
        List<ConnectorDefinition> connectorDefinitions = read(resources);
        validateDefinitions(connectorDefinitions);
    }

    protected void validateDefinitions(List<ConnectorDefinition> connectorDefinitions) {
        Set<String> duplicates = new HashSet<>();
        for (ConnectorDefinition connectorDefinition : connectorDefinitions) {
            String name = connectorDefinition.getName();
            if (name == null || name.isEmpty()) {
                throw new IllegalStateException("connectorDefinition name cannot be null or empty");
            }
            if (name.contains(".")) {
                throw new IllegalStateException("connectorDefinition name cannot have '.' character");
            }
            if (!duplicates.add(name)) {
                throw new IllegalStateException("More than one connectorDefinition with name '" + name + "' was found. Names must be unique.");
            }
        }
    }

    private List<ConnectorDefinition> read(List<Resource> resources) throws IOException {
        List<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
        for (Resource resource : resources) {
            try (InputStream inputStream = resource.getInputStream()) {
                connectorDefinitions.add(connectorReader.read(inputStream));
            }
        }
        return connectorDefinitions;
    }
}
