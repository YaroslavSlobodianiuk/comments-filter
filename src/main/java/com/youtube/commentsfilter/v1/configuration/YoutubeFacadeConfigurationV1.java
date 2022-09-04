package com.youtube.commentsfilter.v1.configuration;

import com.youtube.commentsfilter.v1.facade.YoutubeFacadeV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;

@Configuration
public class YoutubeFacadeConfigurationV1 {

    private static final String DEVELOPER_KEY = "KEY_PLACEHOLDER";
    private static final String APPLICATION_NAME = "Comments filter application";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Bean
    public YoutubeFacadeV1 youtubeFacade(YouTube youTube) {
        return new YoutubeFacadeV1(DEVELOPER_KEY, youTube);
    }

    @Bean
    public YouTube youTube() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
