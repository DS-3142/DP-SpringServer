package com.project.ds.repository;

import com.project.ds.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("SELECT m FROM Meeting m WHERE m.summary LIKE %:keyword%")
    List<Meeting> findBySummaryContaining(@Param("keyword") String keyword);
}
