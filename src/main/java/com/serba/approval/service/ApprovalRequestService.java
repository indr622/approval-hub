package com.serba.approval.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serba.approval.dto.ApprovalRequestDto;
import com.serba.approval.dto.CreateRequestApproval;
import com.serba.approval.entity.ApprovalRequestEntity;
import com.serba.approval.mapper.ApprovalRequestMapper;
import com.serba.approval.repository.ApprovalRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class ApprovalRequestService {
    private final ApprovalRequestRepository approvalRequestRepository;
    private final ApprovalRequestMapper approvalRequestMapper;
    private final ObjectMapper objectMapper;

    public ApprovalRequestDto create(CreateRequestApproval request) {
        ApprovalRequestEntity entity = new ApprovalRequestEntity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPayload(request.getPayload());
        entity.setStatus("PENDING");
        approvalRequestRepository.save(entity);

        return approvalRequestMapper.toDto(entity);
    }

    public List<ApprovalRequestDto> getAll() {
        return approvalRequestRepository.findAll().stream().map(approvalRequestMapper::toDto).toList();
    }

    public ApprovalRequestDto getById(java.util.UUID id) {
        ApprovalRequestEntity entity = approvalRequestRepository
                .findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Approval Request not found"));
        return approvalRequestMapper.toDto(entity);
    }

    public ApprovalRequestDto update(java.util.UUID id, CreateRequestApproval request) {
        ApprovalRequestEntity entity = approvalRequestRepository
                .findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Approval Request not found"));

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPayload(request.getPayload());


        approvalRequestRepository.save(entity);
        return approvalRequestMapper.toDto(entity);
    }

    public void delete(java.util.UUID id) {
        ApprovalRequestEntity entity = approvalRequestRepository
                .findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Approval Request not found"));
        approvalRequestRepository.delete(entity);
    }
}
