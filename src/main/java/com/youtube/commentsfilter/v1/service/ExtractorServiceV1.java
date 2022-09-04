package com.youtube.commentsfilter.v1.service;

import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.youtube.commentsfilter.v1.facade.YoutubeFacadeV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExtractorServiceV1 {

    private final YoutubeFacadeV1 youtubeFacadeV1;

    public void extractCommentThreads(String videoId, List<CommentThread> comments) {
        String pageToken = "";
        CommentThreadListResponse response;
        try {
            do {
                response = youtubeFacadeV1.extractComments(videoId, pageToken);
                if (response != null) {
                    comments.addAll(response.getItems());
                } else {
                    return;
                }
            } while ((pageToken = response.getNextPageToken()) != null);
        } catch (IOException e) {
            log.error("Exception during comments extraction: ", e);
        }
    }

    public List<String> extractVideoIds(String channelId) {
        String pageToken = "";
        PlaylistItemListResponse response;
        List<String> videoIds = new ArrayList<>();
        String playlistId = getPlaylistIdByChannelId(channelId);
        try {
            do {
                response = youtubeFacadeV1.extractVideoIds(playlistId, pageToken);
                for (PlaylistItem item : response.getItems()) {
                    videoIds.add(item.getSnippet().getResourceId().getVideoId());
                }
            } while ((pageToken = response.getNextPageToken()) != null);
        } catch (Exception e) {
            log.error("Exception during video ids extractions: ", e);
        }
        return videoIds;
    }

    private String getPlaylistIdByChannelId(String channelId) {
        return channelId.substring(0,1) + 'U' + channelId.substring(2);
    }

}
