package com.project.ds.dto;

import com.project.ds.domain.Paper;

public record PaperDto(String title, String summary, String url) {
    public static PaperDto of(Paper paper) {
        return new PaperDto(paper.getTitle(), paper.getSummary(), paper.getUrl());
    }
}
