package com.Mattheo992.githubRepoInformator.controller;

import com.Mattheo992.githubRepoInformator.model.RepositoryDetails;
import com.Mattheo992.githubRepoInformator.service.GithubRepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    GithubRepositoryService githubRepositoryService;

    @Test
    public void GetRepositoryDetails_RepositoryExists_DetailsReturned() throws Exception {

        RepositoryDetails repositoryDetails1 = new RepositoryDetails();
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

    @Test
    public void GetRepositoryDetails_RepositoryNotExists_DetailsReturned() throws Exception {
        String owner = "DominoJahas";
        String repositoryName = "NonExistingRepo";

        when(githubRepositoryService.getRepositoryDetails(owner, repositoryName))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Repository not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/DominoJahas/NonExistingRepo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string("Repository not found"));
    }
}