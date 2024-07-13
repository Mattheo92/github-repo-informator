package com.Mattheo992.github_repo_informator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Service
@AllArgsConstructor
public class GithubRepositoryService {

    private GithubClient githubClient;

    public RepositoryDetails getRepositoryDetails(String owner, String repo) {
        GithubRepository githubRepository = githubClient.getRepository(owner, repo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime createdAtDateTime = githubRepository.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String formattedDate = createdAtDateTime.format(formatter);

        RepositoryDetails details = new RepositoryDetails();
        details.setFullName(githubRepository.getFullName());
        details.setDescription(githubRepository.getDescription());
        details.setCloneUrl(githubRepository.getCloneUrl());
        details.setStars(githubRepository.getStars());
        details.setCreatedAt(formattedDate);

        return details;
    }
}