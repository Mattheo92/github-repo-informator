package com.Mattheo992.githubRepoInformator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GithubRepoInformatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubRepoInformatorApplication.class, args);
    }

}