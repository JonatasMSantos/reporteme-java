package me.reporte.analysis.listener;

import me.reporte.analysis.service.CreditAnalysisService;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnalysisProposalListener {

    private final CreditAnalysisService creditAnalysisService;

    public AnalysisProposalListener(CreditAnalysisService creditAnalysisService) {
        this.creditAnalysisService = creditAnalysisService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.pending-proposal}")
    public void pendingProposal(ProposalResponseDTO proposal) {
        creditAnalysisService.analyze(proposal);
    }
}
