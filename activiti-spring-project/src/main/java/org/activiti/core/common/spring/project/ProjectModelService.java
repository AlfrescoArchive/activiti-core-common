package org.activiti.core.common.spring.project;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.core.common.project.model.ProjectManifest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ProjectModelService {


    private String path;

    private String appName;

    private final ObjectMapper objectMapper;

    private ResourcePatternResolver resourceLoader;

    public ProjectModelService(String path,
                               String appName,
                               ObjectMapper objectMapper,
                               ResourcePatternResolver resourceLoader) {
        this.path = path;
        this.appName = appName;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }


    private Optional<Resource> retrieveResource(){

        Resource resource = resourceLoader.getResource(path);
        if (resource.exists()) {
            return Optional.of(resourceLoader.getResource(path + appName + ".json"));
        }else{
            return Optional.empty();
        }
    }

    private ProjectManifest read (InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream,
                                      ProjectManifest.class);
    }

    public ProjectManifest getProjectManifest() throws IOException  {
        Optional<Resource> resourceOptional = retrieveResource();
        if(resourceOptional.isPresent()){
            return read(resourceOptional.get().getInputStream());
        }else{
            throw new NoSuchFileException(appName + ".json");
        }
    }

}
