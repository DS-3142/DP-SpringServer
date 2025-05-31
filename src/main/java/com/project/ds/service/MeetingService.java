package com.project.ds.service;

import com.project.ds.domain.InvertedIndex;
import com.project.ds.domain.Paper;
import com.project.ds.domain.Meeting;
import com.project.ds.dto.SearchType;
import com.project.ds.dto.request.PostMeetingSummaryRequest;
import com.project.ds.dto.response.*;
import com.project.ds.repository.InvertedIndexRepository;
import com.project.ds.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final PythonService pythonService;
    private final MeetingRepository meetingRepository;
    private final InvertedIndexRepository invertedIndexRepository;

    public PostMeetingSummaryResponse summarizeAndSave(PostMeetingSummaryRequest request) {
        Meeting meeting = Meeting.from(request.title());
        Meeting saved = meetingRepository.save(meeting);

        PythonResponse pythonResponse = pythonService.requestSummary(saved.getMeetingId(), request.content());

        saved.setSummary(pythonResponse.summary());
        saved.setKeywords(pythonResponse.keywords());

        meetingRepository.save(saved);

        return new PostMeetingSummaryResponse(
                saved.getMeetingId(),
                saved.getTitle(),
                saved.getSummary(),
                saved.getKeywords()
        );
    }

    // 역색인 검색 기능
    public List<GetSearchConferenceResponse> searchConferencesByKeyword(String keyword, SearchType type) {
        List<Meeting> meetings;

        if (type == SearchType.MEETING) {
            // 회의록 요약 기반 검색
            meetings = meetingRepository.findBySummaryContaining(keyword);
        } else if (type == SearchType.PAPER) {
            // 논문 키워드 역색인 기반 검색
            List<InvertedIndex> indexMatches = invertedIndexRepository.findByPaperWordContainingIgnoreCase(keyword);
            meetings = indexMatches.stream()
                    .map(InvertedIndex::getMeeting)
                    .distinct()
                    .toList();
        } else {
            throw new IllegalArgumentException("Invalid search type. Use 'meeting' or 'paper'.");
        }

        return meetings.stream()
                .map(GetSearchConferenceResponse::of)
                .toList();
    }

    public void savePaper(Long meetingId, String title, String summary, String url) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting ID"));

        Paper paper = Paper.builder()
                .title(title)
                .summary(summary)
                .url(url)
                .meeting(meeting)
                .build();

        meeting.getPapers().add(paper);
        meetingRepository.save(meeting);
    }

    public GetMeetingDetailResponse getMeetingDetail(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting ID"));

        List<RecommendedPaper> paperDtos = meeting.getPapers().stream()
                .map(p -> new RecommendedPaper(p.getTitle(), p.getUrl(), p.getSummary()))
                .toList();

        return new GetMeetingDetailResponse(
                meeting.getTitle(),
                meeting.getSummary(),
                meeting.getKeywords(),
                paperDtos
        );
    }
}
