package com.project.ds.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inverted_index", indexes = {
        @Index(name = "idx_paper_word", columnList = "paper_word"),
        @Index(name = "idx_meeting_id", columnList = "meeting_id")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvertedIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inverted_index_id")
    private Long id;

    @Column(name = "paper_word", nullable = false)
    private String paperWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Builder
    public InvertedIndex(String paperWord, Meeting meeting) {
        this.paperWord = paperWord;
        this.meeting = meeting;
    }
}