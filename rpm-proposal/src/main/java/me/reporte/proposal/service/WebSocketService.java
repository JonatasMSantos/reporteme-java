package me.reporte.proposal.service;

import lombok.AllArgsConstructor;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WebSocketService {

    private final SimpMessagingTemplate template;

    public void notify(ProposalResponseDTO proposal) {
        template.convertAndSend("/proposal", proposal);
    }

}
