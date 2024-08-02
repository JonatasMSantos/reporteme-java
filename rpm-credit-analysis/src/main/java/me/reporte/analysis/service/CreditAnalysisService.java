package me.reporte.analysis.service;

import lombok.AllArgsConstructor;
import me.reporte.analysis.exception.StrategyException;
import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditAnalysisService {

    private final List<PointCalculation> pointCalculationList;

    private final RabbitMQNotificationService rabbitMQNotificationService;

    private final String exchange;

    public CreditAnalysisService(List<PointCalculation> pointCalculationList,
                                 RabbitMQNotificationService rabbitMQNotificationService,
                                 @Value("${rabbitmq.exchange.completed-proposal}") String exchange) {
        this.pointCalculationList = pointCalculationList;
        this.rabbitMQNotificationService = rabbitMQNotificationService;
        this.exchange = exchange;
    }

    public void analyze(ProposalResponseDTO proposal) {
        proposal.getObservation().toLowerCase();
        try {
            int score = pointCalculationList.stream().mapToInt(impl -> impl.calculate(proposal)).sum();
            boolean approved = score > 350;
            proposal.setApproved(approved);
        } catch (StrategyException strategyException) {
            proposal.setApproved(false);
            proposal.setObservation(strategyException.getMessage());
        }

        rabbitMQNotificationService.notify(proposal, exchange);

    }
}
