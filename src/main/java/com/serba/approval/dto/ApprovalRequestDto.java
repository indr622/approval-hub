package com.serba.approval.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalRequestDto {
    private UUID id;
    private String title;
    private String payload;
    private String status;
    private Integer currentLevel;


}
