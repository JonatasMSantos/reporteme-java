package me.reporte.proposal.service;

import lombok.AllArgsConstructor;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RabbitMQNotificationService {

    private RabbitTemplate rabbitTemplate;

    public void notify(ProposalResponseDTO proposal, String exchange) {
//        int priority = proposal.getIncome() > 10000 ? 10 : 5;
//
//        MessagePostProcessor messagePostProcessor = message -> {
//            message.getMessageProperties().setPriority(priority);
//            return message;
//        };
//
//        rabbitTemplate.convertAndSend(exchange, "", proposal, messagePostProcessor);
        rabbitTemplate.convertAndSend(exchange, "", proposal);
    }
}
