package com.project.ds.repository;

import com.project.ds.domain.InvertedIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InvertedIndexRepository extends JpaRepository<InvertedIndex, Long> {
    List<InvertedIndex> findByPaperWordContainingIgnoreCase(String word);
}
