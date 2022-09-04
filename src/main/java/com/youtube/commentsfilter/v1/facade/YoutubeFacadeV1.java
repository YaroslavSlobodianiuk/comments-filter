package com.youtube.commentsfilter.v1.facade;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class YoutubeFacadeV1 {

    private final String developerKey;
    private final YouTube youTube;

    public CommentThreadListResponse extractComments(String videoId, String pageToken) throws IOException {
        CommentThreadListResponse response = null;
        try {
            response = youTube.commentThreads().list("id, snippet, replies")
                    .setKey(developerKey)
                    .setVideoId(videoId)
                    .setPageToken(pageToken)
                    .setMaxResults(100L)
                    .execute();
        } catch (GoogleJsonResponseException ex) {
            log.warn("Error during extraction: {}", ex.getDetails().getErrors());
        }
        return response;
    }

    public PlaylistItemListResponse extractVideoIds(String playlistId, String pageToken) throws IOException {
        YouTube.PlaylistItems.List request = youTube.playlistItems().list("snippet");
        if (!Objects.equals(pageToken, "")) {
            request.setPageToken(pageToken);
        }
        return request.setKey(developerKey)
                .setPlaylistId(playlistId)
                .setPageToken(pageToken)
                .setMaxResults(50L)
                .execute();
    }

}
