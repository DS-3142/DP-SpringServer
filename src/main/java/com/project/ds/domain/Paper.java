package com.project.ds.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paper")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Long paperId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Lob
    @Column(name = "summary", nullable = false, columnDefinition = "LONGTEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Builder
    public Paper(String title, String url, String summary, Meeting meeting) {
        this.title = title;
        this.url = url;
        this.summary = summary;
        this.meeting = meeting;
    }
}
