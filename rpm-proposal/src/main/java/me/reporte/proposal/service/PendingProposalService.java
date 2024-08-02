package me.reporte.proposal.service;

import me.reporte.core.dto.ProposalRequestDTO;
import me.reporte.core.dto.ProposalResponseDTO;
import me.reporte.proposal.mapper.ProposalMapper;
import me.reporte.core.entity.Proposal;
import me.reporte.core.repository.ProposalRepository;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class PendingProposalService implements Serializable {

    private final ProposalRepository proposalRepository;
    private final RabbitMQNotificationService rabbitMQNotificationService;

    private final String exchange;

    public PendingProposalService(ProposalRepository proposalRepository,
                                  RabbitMQNotificationService rabbitMQNotificationService,
                                  @Value("${rabbitmq.exchange.pending-proposal}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.rabbitMQNotificationService = rabbitMQNotificationService;
        this.exchange = exchange;
    }

    public ProposalResponseDTO create(ProposalRequestDTO req) {
        Proposal proposal = ProposalMapper.INSTANCE.convertDTOTOProposal(req);
        Proposal savedProposal = proposalRepository.save(proposal);

        ProposalResponseDTO proposalDTO = ProposalMapper.INSTANCE.convertEntityToDTO(savedProposal);
        notifyRabbitMQ(proposalDTO);

        return proposalDTO;
    }

    public List<ProposalResponseDTO> findAll() {
        Iterable<Proposal> proposals = proposalRepository.findAll();
        return ProposalMapper.INSTANCE.convertListEntityToListDTO(proposals);
    }

    public void notifyRabbitMQ(ProposalResponseDTO proposal) {
        try {
            rabbitMQNotificationService.notify(proposal, exchange);
        } catch (RuntimeException ex) {
            Proposal updatedProposal = new Proposal(proposal.getId());
            updatedProposal.setIntegrated(false);
            proposalRepository.save(updatedProposal);
        }
    }

}
