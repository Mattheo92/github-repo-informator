package com.Mattheo992.github_repo_informator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Date;
@Getter
@Repository
public class GithubRepository {


    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("stargazers_count")
    private int stars;

    @JsonProperty("created_at")
    private Date createdAt;
}