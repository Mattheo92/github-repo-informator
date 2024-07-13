package com.Mattheo992.githubRepoInformator.retryer;

import com.Mattheo992.githubRepoInformator.decoder.CustomErrorDecoder;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public Retryer retryer(){
        return new Retryer.Default(1000, 1000, 5);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}