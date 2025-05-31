package com.project.ds.dto.response;

import java.util.List;

public record PythonResponse(
        Long meetingId,
        String summary,
        List<String> keywords
){ }
