package com.youtube.commentsfilter.v1.service;

import com.google.api.services.youtube.model.CommentThread;
import com.youtube.commentsfilter.v1.model.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CommentsFilterServiceV1 {
    private final ExtractorServiceV1 extractorServiceV1;
    private final Map<String, BiPredicate<CommentThread, Map<String, String>>> biPredicates;

    public List<Comment> getFilteredVideoComments(String videoId, Map<String, String> filterParams) {
        List<CommentThread> commentThreads = new ArrayList<>();
        extractCommentThreads(videoId, commentThreads);
        return mapToComment(filterCommentThreads(commentThreads, filterParams));
    }

    public List<Comment> getFilteredChannelComments(String channelId, Map<String, String> filterParams) {
        List<CommentThread> commentThreads = new ArrayList<>();
        List<String> videoIds = extractorServiceV1.extractVideoIds(channelId);
        for (String id : videoIds) {
            extractCommentThreads(id, commentThreads);
        }
        return mapToComment(filterCommentThreads(commentThreads, filterParams));
    }

    private void extractCommentThreads(String videoId, List<CommentThread> commentThreads) {
        extractorServiceV1.extractCommentThreads(videoId, commentThreads);
    }

    private List<CommentThread> filterCommentThreads(List<CommentThread> commentThreads, Map<String, String> filterParams) {
        BiPredicate<CommentThread, Map<String, String>> filter = filterParams.keySet()
                .stream()
                .map(biPredicates::get)
                .reduce(BiPredicate::and)
                .orElse((predicate, params) -> true);

        return commentThreads.stream()
                .filter(commentThread -> filter.test(commentThread, filterParams))
                .collect(Collectors.toList());
    }

    private List<Comment> mapToComment(List<CommentThread> commentThreads) {
        return commentThreads.stream()
                .map(Comment::toComment)
                .collect(Collectors.toList());
    }

}
