package me.reporte.analysis.service;

import lombok.AllArgsConstructor;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RabbitMQNotificationService {

    private RabbitTemplate rabbitTemplate;

    public void notify(ProposalResponseDTO proposal, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposal);
    }
}
