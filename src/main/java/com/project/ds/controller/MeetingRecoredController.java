package com.project.ds.controller;

import com.project.ds.dto.request.PostMeetingSummaryRequest;
import com.project.ds.dto.response.PostMeetingSummaryResponse;
import com.project.ds.dto.response.GetSearchConferenceResponse;
import com.project.ds.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
public class MeetingRecoredController {

    private final MeetingService meetingRecordService;

    @PostMapping("/summarize")
    public ResponseEntity<PostMeetingSummaryResponse> summarize(@ModelAttribute PostMeetingSummaryRequest request) {
        return ResponseEntity.ok(meetingRecordService.summarizeAndSave(request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GetSearchConferenceResponse>> searchConferencesByKeyword(@RequestParam(name="keyword") String keyword) {
        return ResponseEntity.ok(meetingRecordService.searchConferencesByKeyword(keyword));
    }
}
