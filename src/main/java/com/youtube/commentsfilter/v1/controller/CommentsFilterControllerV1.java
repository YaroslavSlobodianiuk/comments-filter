package com.youtube.commentsfilter.v1.controller;

import com.youtube.commentsfilter.v1.model.Comment;
import com.youtube.commentsfilter.v1.service.CommentsFilterServiceV1;
import com.youtube.commentsfilter.validator.FilterParamsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "comments-filter")
@RequiredArgsConstructor
public class CommentsFilterControllerV1 {

    private final FilterParamsValidator filterParamsValidator;
    private final CommentsFilterServiceV1 commentsFilterServiceV1;

    @GetMapping(path = "/v1/video/{videoId}/comments")
    public List<Comment> getVideoComments(@PathVariable String videoId,
                                          @RequestParam Map<String, String> filterParams) {
        filterParamsValidator.validate(filterParams);
        return commentsFilterServiceV1.getFilteredVideoComments(videoId, filterParams);
    }

    @GetMapping(path = "/v1/channel/{channelId}/comments")
    public List<Comment> getChannelComments(@PathVariable String channelId,
                                   @RequestParam Map<String, String> filterParams) {
        filterParamsValidator.validate(filterParams);
        return commentsFilterServiceV1.getFilteredChannelComments(channelId, filterParams);
    }

}
