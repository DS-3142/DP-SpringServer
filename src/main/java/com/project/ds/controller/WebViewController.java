package com.project.ds.controller;

import com.project.ds.dto.request.PostMeetingSummaryRequest;
import com.project.ds.dto.response.PostMeetingSummaryResponse;
import com.project.ds.dto.response.GetSearchConferenceResponse;
import com.project.ds.service.MeetingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class WebViewController {

    private final MeetingRecordService meetingRecordService;

    // 첫 진입 화면
    @GetMapping("/home")
    public String showForm(Model model) {
        model.addAttribute("postRequest", new PostMeetingSummaryRequest("", ""));
        return "meeting";
    }

    // 회의 요약 요청 처리
    @PostMapping("/summarize")
    public String summarizeMeeting(
            @ModelAttribute PostMeetingSummaryRequest request,
            Model model
    ) {
        PostMeetingSummaryResponse summaryResponse = meetingRecordService.summarizeAndSave(request);
        model.addAttribute("postRequest", request);
        model.addAttribute("summaryResponse", summaryResponse);
        return "meeting";
    }

    // 회의 검색 처리
    @GetMapping("/search")
    public String searchConferences(
            @RequestParam("keyword") String keyword,
            Model model
    ) {
        List<GetSearchConferenceResponse> searchResults = meetingRecordService.searchConferencesByKeyword(keyword);
        model.addAttribute("postRequest", new PostMeetingSummaryRequest("", ""));
        model.addAttribute("searchResults", searchResults);
        return "meeting";
    }

    // 회의 상세 정보 (선택된 회의와 추천 논문)
    @GetMapping("/conference/{title}")
    public String showConferenceDetail(
            @PathVariable("title") String title,
            Model model
    ) {
        List<GetSearchConferenceResponse> conferences = meetingRecordService.searchConferencesByKeyword(title);
        model.addAttribute("conferences", conferences);
        return "conference-detail";
    }
}