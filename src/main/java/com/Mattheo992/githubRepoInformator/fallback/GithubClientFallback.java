package com.Mattheo992.githubRepoInformator.fallback;

import com.Mattheo992.githubRepoInformator.client.GithubClient;
import com.Mattheo992.githubRepoInformator.repository.GithubRepository;
import org.springframework.stereotype.Component;

@Component
public class GithubClientFallback implements GithubClient {

    @Override
    public GithubRepository getRepository(String owner, String repo) {
        throw new RuntimeException("Repository not found or repository is unavailable");
    }
}