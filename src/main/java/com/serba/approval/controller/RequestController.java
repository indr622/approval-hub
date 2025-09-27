package com.serba.approval.controller;

import com.serba.approval.dto.ApprovalRequestDto;
import com.serba.approval.dto.CreateRequestApproval;
import com.serba.approval.service.ApprovalRequestService;
import com.serba.approval.util.BasicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {
    private final ApprovalRequestService approvalRequestService;

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<BasicResponse> list() {
        try {
            var requests = approvalRequestService.getAll();
            return ResponseEntity.ok(new BasicResponse("List Approval Requests", requests));
        } catch (Exception e) {
            log.error("Error fetching requests", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BasicResponse("Internal Server Error", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BasicResponse> detail(@PathVariable UUID id) {
        try {
            ApprovalRequestDto dto = approvalRequestService.getById(id);
            return ResponseEntity.ok(new BasicResponse("Approval Request Detail", dto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BasicResponse("Not Found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error fetching request detail", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BasicResponse("Internal Server Error", e.getMessage()));
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<BasicResponse> create(@Valid @RequestBody CreateRequestApproval request) {
        try {
            ApprovalRequestDto createdRequest = approvalRequestService.create(request);
            return ResponseEntity.ok(new BasicResponse("Create Approval Success", createdRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BasicResponse("Validation Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BasicResponse("Internal Server Error", e.getMessage()));
        }
    }

    // UPDATE
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BasicResponse> update(@PathVariable UUID id, @RequestBody CreateRequestApproval request) {
        try {
            ApprovalRequestDto updated = approvalRequestService.update(id, request);
            return ResponseEntity.ok(new BasicResponse("Update Approval Success", updated));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BasicResponse("Not Found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating approval request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse("Internal Server Error", e.getMessage()));
        }
    }

    // DELETE
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BasicResponse> delete(@PathVariable UUID id) {
        try {
            approvalRequestService.delete(id);
            return ResponseEntity.ok(new BasicResponse("Delete Approval Success", id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BasicResponse("Not Found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting approval request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse("Internal Server Error", e.getMessage()));
        }
    }
}
