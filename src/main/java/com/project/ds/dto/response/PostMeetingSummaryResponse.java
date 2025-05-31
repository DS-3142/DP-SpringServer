package com.project.ds.dto.response;

import java.util.List;

public record PostMeetingSummaryResponse(
        Long meetingId,
        String title,
        String summary,
        List<String> keywords
) { }
