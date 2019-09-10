package org.activiti.core.common.spring.project.autoconfigure;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.core.common.project.model.ProjectManifest;
import org.activiti.core.common.spring.project.ProjectModelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class ProjectModelConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass(value = "org.springframework.http.converter.json.Jackson2ObjectMapperBuilder")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ProjectModelService projectModelService(@Value("${activiti.application.manifest.file.path}") String path,
                                                   @Value("${activiti.cloud.application.name}") String applicationName,
                                                   ObjectMapper objectMapper,
                                                   ResourcePatternResolver resourceLoader) {
        return new ProjectModelService(path,
                                       applicationName,
                                       objectMapper,
                                       resourceLoader);
    }

    @Bean
    @ConditionalOnProperty(name = "activiti.application.manifest.file.path")
    public ProjectManifest projectManifest(ProjectModelService projectModelService) throws IOException {
        return projectModelService.getProjectManifest();
    }
}
