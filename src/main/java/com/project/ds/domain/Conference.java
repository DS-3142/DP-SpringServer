package com.project.ds.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conference")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private Long conferenceId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Lob
    @Column(name = "summary", nullable = false, columnDefinition = "LONGTEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_record_id", nullable = false)
    private MeetingRecord meetingRecord;

    @Builder
    public Conference(String title, String url, String summary) {
        this.title = title;
        this.url = url;
        this.summary = summary;
    }
}
