package com.project.ds.dto.response;

import java.util.List;

public record PythonResponse(
        String summary,
        List<String> keywords,
        List<RecommendedPaper> recommendedPapers
){ }
