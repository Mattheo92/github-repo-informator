package com.Mattheo992.githubRepoInformator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RepositoryDetails {

    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private String createdAt;
}