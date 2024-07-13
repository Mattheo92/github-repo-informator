package com.Mattheo992.githubRepoInformator.decoder;


import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;


public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 503) {
            return new RetryableException(
                    response.status(),
                    "Sorry, but service is unavailable now",
                    response.request().httpMethod(),
                    50L,
                    response.request());
        }
        return feign.FeignException.errorStatus(methodKey, response);
    }
}