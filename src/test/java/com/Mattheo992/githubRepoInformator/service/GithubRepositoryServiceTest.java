package com.Mattheo992.githubRepoInformator.service;

import com.Mattheo992.githubRepoInformator.client.GithubClient;
import com.Mattheo992.githubRepoInformator.model.RepositoryDetails;
import com.Mattheo992.githubRepoInformator.repository.GithubRepository;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class GithubRepositoryServiceTest {

    @Mock
    private GithubClient githubClient;

    private GithubRepositoryService githubRepositoryService;

    @BeforeEach
    public void setup() {
        githubRepositoryService = new GithubRepositoryService(githubClient);
    }

    @Test
    public void testGetRepositoryDetails_PositiveCase() {

        String owner = "tymon";
        String repositoryName = "test";
        GithubRepository mockGithubRepository = new GithubRepository();
        mockGithubRepository.setFullName("tymon/test");
        mockGithubRepository.setDescription("My first repository on GitHub");
        mockGithubRepository.setCloneUrl("https://github.com/tymon/test.git");
        mockGithubRepository.setStars(100);
        mockGithubRepository.setCreatedAt(LocalDateTime.parse("2020-01-01T12:00"));

        Mockito.when(githubClient.getRepository(owner, repositoryName))
                .thenReturn(mockGithubRepository);

        RepositoryDetails result = githubRepositoryService.getRepositoryDetails(owner, repositoryName);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("tymon/test", result.getFullName());
        Assertions.assertEquals("My first repository on GitHub", result.getDescription());
        Assertions.assertEquals("https://github.com/tymon/test.git", result.getCloneUrl());
        Assertions.assertEquals(100, result.getStars());
        Assertions.assertEquals("2020-01-01T12:00", result.getCreatedAt());
    }

    @Test
    public void testGetRepositoryDetails_RepositoryNotFound() {

        String owner = "tymon";
        String repositoryName = "nonexistent-repo";

        Mockito.when(githubClient.getRepository(owner, repositoryName))
                .thenThrow(FeignException.NotFound.class);

        Assertions.assertThrows(FeignException.NotFound.class, () -> {
            githubRepositoryService.getRepositoryDetails(owner, repositoryName);
        });
    }
}