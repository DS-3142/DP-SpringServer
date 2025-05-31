package com.project.ds.dto;

public enum SearchType {
    MEETING, PAPER;

    public static SearchType from(String type) {
        try {
            return SearchType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 검색 타입입니다. (meeting 또는 paper)");
        }
    }
}
