package com.serba.approval.mapper;

import com.serba.approval.dto.CreateRequestApproval;
import com.serba.approval.dto.ApprovalRequestDto;
import com.serba.approval.entity.ApprovalRequestEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class ApprovalRequestMapper {

    public ApprovalRequestDto toDto(ApprovalRequestEntity entity) {
        return ApprovalRequestDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .payload(entity.getPayload())
                .status(entity.getStatus())
                .currentLevel(entity.getCurrentLevel())
                .build();
    }

    public ApprovalRequestEntity toEntity(CreateRequestApproval req) {
        ApprovalRequestEntity entity = new ApprovalRequestEntity();
        entity.setTitle(req.getTitle());
        entity.setDescription(req.getDescription());
        entity.setPayload(req.getPayload());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return entity;
    }
}
