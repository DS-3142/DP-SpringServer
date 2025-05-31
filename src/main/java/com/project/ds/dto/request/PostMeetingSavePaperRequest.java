package com.project.ds.dto.request;

public record PostMeetingSavePaperRequest(
        Long meetingId,
        String title,
        String summary,
        String url
) {
}
