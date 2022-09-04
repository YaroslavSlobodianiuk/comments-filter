package com.youtube.commentsfilter.v1.configuration;

import com.youtube.commentsfilter.validator.FilterParamsConfig;
import com.youtube.commentsfilter.validator.FilterParamsValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentsFilterControllerConfigurationV1 {

    @Bean
    public FilterParamsValidator filterParamsValidator(FilterParamsConfig filterParamsConfig) {
        return new FilterParamsValidator(filterParamsConfig.getValidParams());
    }

}
