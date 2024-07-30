package me.reporte.proposal.scheduler;

import me.reporte.proposal.mapper.ProposalMapper;
import me.reporte.core.dto.ProposalResponseDTO;
import me.reporte.core.entity.Proposal;
import me.reporte.core.repository.ProposalRepository;
import me.reporte.proposal.service.RabbitMQNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProposalWithoutIntegration {

    private final ProposalRepository proposalRepository;
    private final RabbitMQNotificationService rabbitMQNotificationService;
    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(ProposalWithoutIntegration.class);

    public ProposalWithoutIntegration(ProposalRepository proposalRepository,
                                      RabbitMQNotificationService rabbitMQNotificationService,
                                      @Value("${rabbitmq.exchange.pending-proposal}") String exchange)  {
        this.proposalRepository = proposalRepository;
        this.rabbitMQNotificationService = rabbitMQNotificationService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void findProposalWithoutIntegration() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(proposal -> {
            try {
                ProposalResponseDTO proposalDTO = ProposalMapper.INSTANCE.convertEntityToDTO(proposal);
                markProsalIntegration(proposal);
                rabbitMQNotificationService.notify(proposalDTO, this.exchange);
            } catch (RuntimeException re) {
                logger.error(re.getMessage());
            }
        });
    }

    public void markProsalIntegration(Proposal proposal) {
        proposal.setIntegrated(true);
        proposalRepository.save(proposal);
    }
}
