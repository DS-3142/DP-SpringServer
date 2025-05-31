package com.project.ds.dto.response;

import java.util.List;

public record GetMeetingDetailResponse(
        String title,
        String summary,
        List<String> keywords,
        List<RecommendedPaper> recommendedPapers
) {
}
