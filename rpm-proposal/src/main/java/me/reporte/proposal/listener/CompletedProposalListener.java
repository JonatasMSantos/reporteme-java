package me.reporte.proposal.listener;

import lombok.AllArgsConstructor;
import me.reporte.core.dto.ProposalResponseDTO;
import me.reporte.core.repository.ProposalRepository;
import me.reporte.proposal.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class CompletedProposalListener {


    private final WebSocketService webSocketService;

    private final ProposalRepository proposalRepository;

    @RabbitListener(queues = "${rabbitmq.queue.completed-proposal}")
    public void completedProposal(ProposalResponseDTO proposal) {
        updateProposal(proposal);
        webSocketService.notify(proposal);
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending-proposal}")
    public void pendingProposal(ProposalResponseDTO proposal) {
        //updateProposal(proposal);
        webSocketService.notify(proposal);
    }

    private void updateProposal(ProposalResponseDTO proposal) {
        proposalRepository.updateProposal(proposal.getId(), proposal.getApproved(), proposal.getObservation());
    }
}
