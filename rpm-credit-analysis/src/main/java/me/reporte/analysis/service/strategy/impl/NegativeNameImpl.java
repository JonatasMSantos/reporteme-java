package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.constant.MessagesConstant;
import me.reporte.analysis.exception.StrategyException;
import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1)
@Component
public class NegativeNameImpl implements PointCalculation {

    @Override
    public int calculate(ProposalResponseDTO proposal) {
        boolean clientSituation = verifyClientSituation(proposal.getDocument());
        if (clientSituation) {
            String message = String.format(MessagesConstant.DENIED_PROPOSAL, proposal.getFirstName(), "Client with negative status.");
            throw new StrategyException(message);
        }
        return 100;
    }

    private boolean verifyClientSituation(String document) {
        return new Random().nextBoolean();
    }
}
