package com.project.ds.repository;

import com.project.ds.domain.MeetingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRecordRepository extends JpaRepository<MeetingRecord, Long> {
    @Query("SELECT mr FROM MeetingRecord mr JOIN mr.keywords k WHERE k LIKE %:keyword%")
    List<MeetingRecord> findByKeyword(@Param("keyword") String keyword);
}
