package com.project.ds.service;

import com.project.ds.domain.Conference;
import com.project.ds.domain.MeetingRecord;
import com.project.ds.dto.request.PostMeetingSummaryRequest;
import com.project.ds.dto.response.PostMeetingSummaryResponse;
import com.project.ds.dto.response.GetSearchConferenceResponse;
import com.project.ds.dto.response.PythonResponse;
import com.project.ds.dto.response.RecommendedPaper;
import com.project.ds.repository.MeetingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingRecordService {
    private final PythonService pythonService;
    private final MeetingRecordRepository meetingRecordRepository;

    public PostMeetingSummaryResponse summarizeAndSave(PostMeetingSummaryRequest request) {
        PythonResponse summary = pythonService.requestSummary(request.content());

        List<Conference> conferences = summary.recommendedPapers().stream()
                .map(c -> Conference.builder()
                        .title(c.title())
                        .url(c.url())
                        .summary(c.summary())
                        .build()
                )
                .toList();

        MeetingRecord record = MeetingRecord.builder()
                .title(request.title())
                .summary(summary.summary())
                .keywords(summary.keywords())
                .conferences(conferences)
                .build();

        conferences.forEach(c -> c.setMeetingRecord(record));
        meetingRecordRepository.save(record);

        return new PostMeetingSummaryResponse(
                request.title(),
                summary.summary(),
                summary.keywords(),
                summary.recommendedPapers()
        );
    }

    // 역색인 검색 기능
    public List<GetSearchConferenceResponse> searchConferencesByKeyword(String keyword) {
        List<MeetingRecord> records = meetingRecordRepository.findByKeyword(keyword);
        if (records.isEmpty()) {    // []빈 리스트를 클라이언트가 받으면 "검색 결과가 없습니다" 등의 메시지를 프론트에서 보여주도록
            return List.of();
        }

        return records.stream()
                .flatMap(r -> r.getConferences().stream())
                .map(GetSearchConferenceResponse::of).toList();
    }
}
