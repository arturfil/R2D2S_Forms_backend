package com.arturofilio.artus_forms.models.responses;

import java.util.List;

import lombok.Data;

@Data
public class PaginatedPollRest {
    private List<PollRest> polls;
    private int totalPages;
    private long totalRecords;
    private long currentPageRecords;
    private int currentPage;
}
