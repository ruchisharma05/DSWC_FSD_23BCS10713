package com.devtrack.tickets.controller;

import com.devtrack.tickets.dto.TicketRequestDTO;
import com.devtrack.tickets.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<String> createTicket(@Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        String response = ticketService.createTicket(ticketRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
