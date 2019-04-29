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

import java.util.Arrays;
import java.util.Collections;

import org.activiti.core.common.model.connector.ConnectorDefinition;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConnectorResourceFinderDescriptorTest {

    private ConnectorResourceFinderDescriptor resourceFinderDescriptor;

    private ConnectorReader reader;

    @Before
    public void setUp() {
        initMocks(this);
        resourceFinderDescriptor = new ConnectorResourceFinderDescriptor(true,
                                                                         "my/prefix/for/connectors/",
                                                                         reader);
    }

    @Test
    public void getLocationSuffixesShouldReturnJsonExtension() {
        assertThat(resourceFinderDescriptor.getLocationSuffixes())
                .containsExactly("**.json");
    }

    @Test
    public void getLocationPrefixShouldReturnPrefixReceidAsConstructorParameter() {
        assertThat(resourceFinderDescriptor.getLocationPrefix()).isEqualTo("my/prefix/for/connectors/");
    }

    @Test
    public void shouldLookUpResourcesShouldReturnResultReceivedAsConstructorParameter() {
        assertThat(new ConnectorResourceFinderDescriptor(true,
                                              "my/prefix/for/connectors/",
                                              reader).shouldLookUpResources()).isTrue();
        assertThat(new ConnectorResourceFinderDescriptor(false,
                                              "my/prefix/for/connectors/",
                                              reader).shouldLookUpResources()).isFalse();
    }

    @Test
    public void validateShouldThrowExceptionWhenConnectorNameIsNull() {
        //given
        ConnectorDefinition connectorDefinition = new ConnectorDefinition();
        connectorDefinition.setName(null);

        //when
        Throwable throwable = catchThrowable(
                () -> resourceFinderDescriptor.validateDefinitions(Collections.singletonList(connectorDefinition))
        );

        //then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("connectorDefinition name cannot be null or empty");
    }

    @Test
    public void validateShouldThrowExceptionWhenConnectorNameIsEmpty() {
        //given
        ConnectorDefinition connectorDefinition = new ConnectorDefinition();
        connectorDefinition.setName("");

        //when
        Throwable throwable = catchThrowable(
                () -> resourceFinderDescriptor.validateDefinitions(Collections.singletonList(connectorDefinition))
        );

        //then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("connectorDefinition name cannot be null or empty");
    }

    @Test
    public void validateShouldThrowExceptionWhenConnectorNameContainsDotCharacter() {
        //given
        ConnectorDefinition connectorDefinition = new ConnectorDefinition();
        connectorDefinition.setName("connector.name");

        //when
        Throwable throwable = catchThrowable(
                () -> resourceFinderDescriptor.validateDefinitions(Collections.singletonList(connectorDefinition))
        );

        //then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("connectorDefinition name cannot have '.' character");
    }

    @Test
    public void validateShouldThrowExceptionWhenTwoConnectorsHaveTheSameName() {
        //given
        ConnectorDefinition connectorDefinition = new ConnectorDefinition();
        connectorDefinition.setName("Conflicting name connector");

        ConnectorDefinition connectorDefinitionWithSameName = new ConnectorDefinition();
        connectorDefinitionWithSameName.setName("Conflicting name connector");

        //when
        Throwable throwable = catchThrowable(
                () -> resourceFinderDescriptor.validateDefinitions(Arrays.asList(connectorDefinition,
                                                                        connectorDefinitionWithSameName))
        );

        //then
        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("More than one connectorDefinition with name '" + connectorDefinition.getName() + "' was found. Names must be unique.");
    }
}