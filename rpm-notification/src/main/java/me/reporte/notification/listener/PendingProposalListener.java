package me.reporte.notification.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PendingProposalListener {
    @RabbitListener(queues = "${rabbitmq.queue.pending-proposal}")
    public void pendingProposal() {

    }
}
