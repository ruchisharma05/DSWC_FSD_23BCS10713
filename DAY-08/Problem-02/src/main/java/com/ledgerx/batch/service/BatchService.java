package com.ledgerx.batch.service;

import com.ledgerx.batch.dto.BatchRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    public void acceptBatch(BatchRequestDTO batchRequestDTO) {
        // Simulates handing the validated batch to the processing layer.
    }
}
