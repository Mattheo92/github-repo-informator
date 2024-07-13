package com.Mattheo992.github_repo_informator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    GithubRepositoryService githubRepositoryService;

    @MockBean
    RepositoryDetails repositoryDetails;

    @MockBean
    GithubRepository githubRepository;
    @Test
    public void testGetRepositoryDetails() throws Exception {

        RepositoryDetails repositoryDetails1 = new RepositoryDetails ();
        repositoryDetails1.setFullName("Mattheo992/MedicalClinic");
        repositoryDetails1.setCloneUrl("https://github.com/Mattheo992/MedicalClinic.git");
        repositoryDetails1.setStars(10);
        repositoryDetails1.setCreatedAt(String.valueOf(new Date()));


        when(githubRepositoryService.getRepositoryDetails("Mattheo992", "MedicalClinic")).thenReturn(repositoryDetails1);


        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/Mattheo992/MedicalClinic")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Mattheo992/MedicalClinic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloneUrl").value("https://github.com/Mattheo992/MedicalClinic.git"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars").value(10));


        verify(githubRepositoryService, times(1)).getRepositoryDetails("Mattheo992", "MedicalClinic");
    }
}
