package com.ledgerx.batch.controller;

import com.ledgerx.batch.dto.BatchRequestDTO;
import com.ledgerx.batch.service.BatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batches")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping
    public ResponseEntity<String> ingestBatch(@Valid @RequestBody BatchRequestDTO batchRequestDTO) {
        batchService.acceptBatch(batchRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Batch accepted for processing");
    }
}
