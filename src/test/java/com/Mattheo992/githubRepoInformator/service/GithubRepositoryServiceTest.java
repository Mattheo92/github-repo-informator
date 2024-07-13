package com.Mattheo992.githubRepoInformator.service;

import com.Mattheo992.githubRepoInformator.client.GithubClient;
import com.Mattheo992.githubRepoInformator.model.RepositoryDetails;
import com.Mattheo992.githubRepoInformator.repository.GithubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GithubRepositoryServiceTest {

    @Mock
    private GithubClient githubClient;

    private GithubRepositoryService githubRepositoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        githubRepositoryService = new GithubRepositoryService(githubClient);
    }

    @Test
    public void testGetRepositoryDetails_PositiveCase() {

        String owner = "octocat";
        String repositoryName = "Hello-World";
        GithubRepository mockGithubRepository = new GithubRepository();
        mockGithubRepository.setFullName("octocat/Hello-World");
        mockGithubRepository.setDescription("My first repository on GitHub");
        mockGithubRepository.setCloneUrl("https://github.com/octocat/Hello-World.git");
        mockGithubRepository.setStars(100);
        mockGithubRepository.setCreatedAt(LocalDateTime.parse("2020-01-01T12:00"));


        Mockito.when(githubClient.getRepository(owner, repositoryName))
                .thenReturn(mockGithubRepository);

        RepositoryDetails result = githubRepositoryService.getRepositoryDetails(owner, repositoryName);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("octocat/Hello-World", result.getFullName());
        Assertions.assertEquals("My first repository on GitHub", result.getDescription());
        Assertions.assertEquals("https://github.com/octocat/Hello-World.git", result.getCloneUrl());
        Assertions.assertEquals(100, result.getStars());
        Assertions.assertEquals("2020-01-01T12:00", result.getCreatedAt());
    }
}
