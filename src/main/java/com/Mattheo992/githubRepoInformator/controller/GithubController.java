package com.Mattheo992.githubRepoInformator.controller;

import com.Mattheo992.githubRepoInformator.service.GithubRepositoryService;
import com.Mattheo992.githubRepoInformator.model.RepositoryDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/repositories")
public class GithubController {

    private final GithubRepositoryService githubRepositoryService;

    @Operation(summary = "Get details of Github Repository", description = "Returns information with details of a GitHub repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned repository details"),
            @ApiResponse(responseCode = "404", description = "Repository not found")})
    @GetMapping("/{owner}/{repositoryname}")
    public RepositoryDetails getRepositoryDetails(
            @PathVariable("owner") String owner,
            @PathVariable("repositoryname") String repositoryName) {
        return githubRepositoryService.getRepositoryDetails(owner, repositoryName);
    }
}