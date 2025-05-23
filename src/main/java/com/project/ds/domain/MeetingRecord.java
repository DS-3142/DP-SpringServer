package com.project.ds.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "meeting_record")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_record_id")
    private Long meetingRecordId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "summary", nullable = false, columnDefinition = "LONGTEXT")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "meeting_record_keywords", joinColumns = @JoinColumn(name = "meeting_record_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @OneToMany(mappedBy = "meetingRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conference> conferences;

    @Builder
    public MeetingRecord(String title, String summary, List<String> keywords, List<Conference> conferences) {
        this.title = title;
        this.summary = summary;
        this.keywords = keywords;
        this.conferences = conferences;
    }
}
