package com.youtube.commentsfilter.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConfigurationProperties("comments-filter")
public class FilterParamsConfig {

    @Getter
    private final Map<String,String> validParams;

}
