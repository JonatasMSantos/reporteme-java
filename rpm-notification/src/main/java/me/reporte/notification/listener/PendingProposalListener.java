package me.reporte.notification.listener;

import me.reporte.core.dto.ProposalResponseDTO;
import me.reporte.notification.constant.MessagesConstant;
import me.reporte.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {

    private final NotificationService notification;

    public PendingProposalListener(NotificationService notification) {
        this.notification = notification;
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending-proposal}")
    public void pendingProposal(ProposalResponseDTO proposal) {
        String message = String.format(MessagesConstant.PENDING_PROPOSAL, proposal.getFirstName());
        notification.notify(proposal.getPhoneNumber(), message);
    }
}
