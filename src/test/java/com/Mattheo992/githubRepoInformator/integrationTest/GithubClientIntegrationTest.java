package com.Mattheo992.githubRepoInformator.integrationTest;

import com.Mattheo992.githubRepoInformator.client.GithubClient;
import com.Mattheo992.githubRepoInformator.repository.GithubRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import feign.RetryableException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GithubClientIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private  GithubClient githubClient;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(8081);
        WireMock.configureFor("localhost", 8081);
    }

    @BeforeEach
    public void setupClient() {
        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(context);
        githubClient = feignClientBuilder.forType(GithubClient.class, "githubClient")
                .url("http://localhost:8081")
                .build();
    }

    @Test
    public void GithubTestWithStatus200() throws Exception {
        wireMockServer.start();
        GithubRepository githubRepository = new GithubRepository();
        githubRepository.setFullName("testowner/testrepo");
        githubRepository.setDescription("test");
        githubRepository.setCloneUrl("testowe");
        githubRepository.setStars(1);
        githubRepository.setCreatedAt(LocalDateTime.parse("2023-01-01T00:00:00"));

        wireMockServer.stubFor(get(urlEqualTo("/repos/testowner/testrepo")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(githubRepository))));

        GithubRepository repository = githubClient.getRepository("testowner", "testrepo");

        assertEquals("testowner/testrepo", repository.getFullName());
        assertEquals("test", repository.getDescription());
        assertEquals("testowe", repository.getCloneUrl());
        assertEquals(1, repository.getStars());
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), repository.getCreatedAt());
        wireMockServer.stop();
    }

    @Test
    public void retryerTestFor503Error() throws Exception {
        wireMockServer.start();
        wireMockServer.stubFor(get(urlEqualTo("/repos/testowner/testrepo"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Sorry, but service is unavailable now\"}")));

        RetryableException exception = assertThrows(RetryableException.class, () -> {
            githubClient.getRepository("testowner", "testrepo");
        });

        assertEquals("Sorry, but service is unavailable now", exception.getMessage());
        wireMockServer.stop();
    }
}