package com.youtube.commentsfilter.v1.model;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadReplies;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class Comment {

    String videoId;
    String text;
    String author;
    String authorChannelUrl;
    List<Comment> replies;

    public static Comment toComment(CommentThread commentThread) {
        CommentThreadReplies replies = commentThread.getReplies();
        return Comment.builder()
                .videoId(commentThread.getSnippet().getVideoId())
                .text(commentThread.getSnippet().getTopLevelComment().getSnippet().getTextOriginal())
                .author(commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName())
                .authorChannelUrl(commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorChannelUrl())
                .replies(replies == null ? null : commentThread.getReplies().getComments().stream().map(Comment::toReplyComment).collect(Collectors.toList()))
                .build();
    }

    public static Comment toReplyComment(com.google.api.services.youtube.model.Comment replyComment) {
        return Comment.builder()
                .text(replyComment.getSnippet().getTextOriginal())
                .author(replyComment.getSnippet().getAuthorDisplayName())
                .authorChannelUrl(replyComment.getSnippet().getAuthorChannelUrl())
                .build();
    }

}
