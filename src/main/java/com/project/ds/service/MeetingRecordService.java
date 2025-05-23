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
//        PythonResponse summary = new PythonResponse(
//                "이 회의는 분산 시스템의 구조를 설계하는 데 중점을 두었습니다.",
//                List.of("분산 시스템", "API 설계", "데이터 처리"),
//                List.of(
//                        new RecommendedPaper(
//                                "On Distributed Systems Design",
//                                "https://example.com/paper1",
//                                "이 논문은 분산 시스템의 구성 요소와 통신 방법을 설명합니다."
//                        ),
//                        new RecommendedPaper(
//                                "Scalable API Design in Practice",
//                                "https://example.com/paper2",
//                                "확장 가능한 API 설계를 위한 패턴과 안티패턴을 소개합니다."
//                        )
//                )
//        );

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
