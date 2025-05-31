package com.project.ds.dto.response;

import com.project.ds.domain.Meeting;
import com.project.ds.dto.PaperDto;

import java.util.List;

public record GetSearchConferenceResponse(
        Long meetingId,
        String title,
        String summary,
        List<PaperDto> papers
) {
    public static GetSearchConferenceResponse of(Meeting meeting) {
        List<PaperDto> paperDtos = meeting.getPapers().stream()
                .map(PaperDto::of)
                .toList();
        return new GetSearchConferenceResponse(
                meeting.getMeetingId(),
                meeting.getTitle(),
                meeting.getSummary(),
                paperDtos
        );
    }
}
