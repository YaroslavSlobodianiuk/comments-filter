package com.youtube.commentsfilter.v1.configuration;

import com.google.api.services.youtube.model.CommentThread;
import com.youtube.commentsfilter.v1.facade.YoutubeFacadeV1;
import com.youtube.commentsfilter.v1.service.CommentsFilterServiceV1;
import com.youtube.commentsfilter.v1.service.ExtractorServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.BiPredicate;

@Configuration
public class CommentsFilterServiceConfigurationV1 {

    @Bean
    public Map<String, BiPredicate<CommentThread, Map<String,String>>> predicates() {
        BiPredicate<CommentThread, Map<String,String>> authorPredicate =
                (comment, params) -> comment.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName().equals(params.get("author"));
        BiPredicate<CommentThread, Map<String,String>> textPredicate =
                (comment, params) -> comment.getSnippet().getTopLevelComment().getSnippet().getTextOriginal().equals(params.get("text"));
        return Map.of("author", authorPredicate, "text", textPredicate);
    }

    @Bean
    public CommentsFilterServiceV1 commentsFilterService(ExtractorServiceV1 extractorServiceV1,
                                                         Map<String, BiPredicate<CommentThread, Map<String,String>>> predicates) {
        return new CommentsFilterServiceV1(extractorServiceV1, predicates);
    }

    @Bean
    public ExtractorServiceV1 extractorServiceV1(YoutubeFacadeV1 youtubeFacadeV1) {
        return new ExtractorServiceV1(youtubeFacadeV1);
    }

}
