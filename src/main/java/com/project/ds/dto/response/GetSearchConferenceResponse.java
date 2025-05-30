package com.project.ds.dto.response;

import com.project.ds.domain.Paper;

public record GetSearchConferenceResponse(
        String title,
        String url,
        String summary
) {
    public static GetSearchConferenceResponse of(Paper c) {
        return new GetSearchConferenceResponse(c.getTitle(), c.getUrl(), c.getSummary());
    }
}
