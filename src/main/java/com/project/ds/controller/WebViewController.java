package com.project.ds.controller;

import com.project.ds.dto.SearchType;
import com.project.ds.dto.request.PostMeetingSummaryRequest;
import com.project.ds.dto.response.GetMeetingDetailResponse;
import com.project.ds.dto.response.GetSearchConferenceResponse;
import com.project.ds.dto.response.PostMeetingSummaryResponse;
import com.project.ds.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class WebViewController {

    private final MeetingService meetingRecordService;

    // 첫 진입 화면
    @GetMapping({"", "/"})
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

        String keywordString = String.join(", ", summaryResponse.keywords());
        model.addAttribute("postRequest", request);
        model.addAttribute("summaryResponse", summaryResponse);
        model.addAttribute("keywordString", keywordString);
        return "meeting";
    }

    // 회의 검색 처리
    @GetMapping("/search")
    public String searchConferences(
            @RequestParam("keyword") String keyword,
            @RequestParam(name = "type", defaultValue = "MEETING") String type,
            Model model
    ) {
        List<GetSearchConferenceResponse> searchResults =
                meetingRecordService.searchConferencesByKeyword(keyword, SearchType.from(type));

        model.addAttribute("postRequest", new PostMeetingSummaryRequest("", ""));
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("type", type); // 필요 시 HTML에서 조건 분기로 사용 가능
        return "meeting";
    }

    // 회의 상세 정보 (선택된 회의와 추천 논문)
    @GetMapping("/conference/{meetingId}")
    public String showConferenceDetail(@PathVariable("meetingId") Long meetingId, Model model) {
        GetMeetingDetailResponse detail = meetingRecordService.getMeetingDetail(meetingId);

        model.addAttribute("detail", detail);
        return "meeting_detail";
    }
}