package me.reporte.analysis.listener;

import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AnalysisProposalListener {

    @RabbitListener(queues = "${rabbitmq.queue.pending-proposal}")
    public void pendingProposal(ProposalResponseDTO proposal) {
    }
}
