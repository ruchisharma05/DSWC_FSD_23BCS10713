package com.devtrack.tickets.service;

import com.devtrack.tickets.dto.BugReportDTO;
import com.devtrack.tickets.dto.FeatureRequestDTO;
import com.devtrack.tickets.dto.TicketRequestDTO;
import com.devtrack.tickets.exception.UnsupportedTicketTypeException;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    public String createTicket(TicketRequestDTO ticketRequestDTO) {
        if (ticketRequestDTO instanceof BugReportDTO) {
            return "Bug report created successfully";
        }

        if (ticketRequestDTO instanceof FeatureRequestDTO) {
            return "Feature request created successfully";
        }

        throw new UnsupportedTicketTypeException(ticketRequestDTO.getType());
    }
}
