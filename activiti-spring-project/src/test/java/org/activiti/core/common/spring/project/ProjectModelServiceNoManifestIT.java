package org.activiti.core.common.spring.project;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.activiti.core.common.spring.project.autoconfigure.ProjectModelConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectModelConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(locations = "classpath:application-no-manifest.properties")
public class ProjectModelServiceNoManifestIT {

    @Autowired
    private ProjectModelService projectModelService;

    @Test
    public void should_ThrowException_When_NoManifestPresent() throws IOException {

        //when
        Throwable thrown = catchThrowable(() -> projectModelService.getProjectManifest());

        //then
        assertThat(thrown)
                .isInstanceOf(FileNotFoundException.class)
                .hasMessageContaining("manifest not found");
    }
}