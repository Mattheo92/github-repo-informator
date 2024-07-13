package com.Mattheo992.github_repo_informator.service;

import com.Mattheo992.github_repo_informator.repository.GithubRepository;
import com.Mattheo992.github_repo_informator.client.GithubClient;
import com.Mattheo992.github_repo_informator.model.RepositoryDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;


@Service
@AllArgsConstructor
public class GithubRepositoryService {

    private GithubClient githubClient;

    public RepositoryDetails getRepositoryDetails(String owner, String repo) {
        GithubRepository githubRepository = githubClient.getRepository(owner, repo);
        String formattedDate = githubRepository.getCreatedAt().toString();

        RepositoryDetails details = new RepositoryDetails();
        details.setFullName(githubRepository.getFullName());
        details.setDescription(githubRepository.getDescription());
        details.setCloneUrl(githubRepository.getCloneUrl());
        details.setStars(githubRepository.getStars());
        details.setCreatedAt(formattedDate);

        return details;
    }
}