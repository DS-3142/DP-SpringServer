package com.project.ds.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "meeting")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = "summary", nullable = false, columnDefinition = "LONGTEXT")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "meeting_keyword", joinColumns = @JoinColumn(name = "meeting_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paper> papers;

    @Builder
    private Meeting(String title, String summary, List<String> keywords, List<Paper> conferences) {
        this.title = title;
        this.summary = summary;
        this.keywords = keywords;
        this.papers = conferences;
    }

    public static Meeting from(String title) {
        return Meeting.builder()
                .title(title)
                .summary("") // 기본값
                .keywords(List.of()) // 기본값
                .conferences(List.of()) // 기본값
                .build();
    }
}
