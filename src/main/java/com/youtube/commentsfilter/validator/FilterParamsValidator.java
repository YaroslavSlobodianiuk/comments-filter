package com.youtube.commentsfilter.validator;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class FilterParamsValidator {

    private final Map<String, String> validParams;

    public void validate(Map<String, String> params) {
        for (String param : params.keySet()) {
            if (validParams.get(param) == null) {
                throw new IllegalArgumentException("Unsupported parameter: " + param);
            }
        }
    }

}
