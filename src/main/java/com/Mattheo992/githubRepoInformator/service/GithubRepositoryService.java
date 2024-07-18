package com.Mattheo992.githubRepoInformator.service;

import com.Mattheo992.githubRepoInformator.repository.GithubRepository;
import com.Mattheo992.githubRepoInformator.client.GithubClient;
import com.Mattheo992.githubRepoInformator.model.RepositoryDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GithubRepositoryService {

    private GithubClient githubClient;

    public RepositoryDetails getRepositoryDetails(String owner, String repo) {
        GithubRepository githubRepository = githubClient.getRepository(owner, repo);

        RepositoryDetails details = new RepositoryDetails();
        details.setFullName(githubRepository.getFullName());
        details.setDescription(githubRepository.getDescription());
        details.setCloneUrl(githubRepository.getCloneUrl());
        details.setStars(githubRepository.getStars());
        details.setCreatedAt(githubRepository.getCreatedAt().toString());

        return details;
    }
}