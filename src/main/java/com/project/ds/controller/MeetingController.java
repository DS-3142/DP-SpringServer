package com.project.ds.controller;

import com.project.ds.dto.SearchType;
import com.project.ds.dto.request.PostMeetingSavePaperRequest;
import com.project.ds.dto.request.PostMeetingSummaryRequest;
import com.project.ds.dto.response.GetMeetingDetailResponse;
import com.project.ds.dto.response.PostMeetingSummaryResponse;
import com.project.ds.dto.response.GetSearchConferenceResponse;
import com.project.ds.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/meeting/summarize")
    public ResponseEntity<PostMeetingSummaryResponse> summarize(@ModelAttribute PostMeetingSummaryRequest request) {
        return ResponseEntity.ok(meetingService.summarizeAndSave(request));
    }

    @GetMapping("/meeting/search")
    public ResponseEntity<List<GetSearchConferenceResponse>> searchByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "type", defaultValue = "MEETING") String type
    ) {
        return ResponseEntity.ok(meetingService.searchConferencesByKeyword(keyword, SearchType.from(type)));
    }

    @PostMapping("/save_paper")
    public ResponseEntity<Void> savePaper(@RequestBody PostMeetingSavePaperRequest request) {
        meetingService.savePaper(request.meetingId(), request.title(), request.summary(), request.url());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/meeting/results/{meetingId}")
    public ResponseEntity<GetMeetingDetailResponse> getMeetingDetail(@PathVariable("meetingId") Long meetingId) {
        return ResponseEntity.ok(meetingService.getMeetingDetail(meetingId));
    }
}
