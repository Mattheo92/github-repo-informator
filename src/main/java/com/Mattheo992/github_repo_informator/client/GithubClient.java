package com.Mattheo992.github_repo_informator.client;

import com.Mattheo992.github_repo_informator.repository.GithubRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", url = "https://api.github.com")
public interface GithubClient {

    @GetMapping("/repos/{owner}/{repo}")
    GithubRepository getRepository(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
