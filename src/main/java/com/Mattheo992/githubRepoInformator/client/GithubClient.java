package com.Mattheo992.githubRepoInformator.client;

import com.Mattheo992.githubRepoInformator.repository.GithubRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "githubClient", url = "https://api.github.com"
        ,configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface GithubClient {

    @GetMapping("/repos/{owner}/{repo}")
    GithubRepository getRepository(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}