package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.constant.MessagesConstant;
import me.reporte.analysis.exception.StrategyException;
import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(2)
@Component
public class ScoreNameImpl implements PointCalculation {

    @Override
    public int calculate(ProposalResponseDTO proposal) {
        int clientScore = verifyClientScore(proposal.getDocument());
        if (clientScore < 200) {
            String message = String.format(MessagesConstant.DENIED_PROPOSAL, proposal.getFirstName(), "Score below 200");
            throw new StrategyException(message);
        } else if (clientScore <= 400) {
            return 150;
        } else if (clientScore <= 600) {
            return 180;
        } else {
            return 220;
        }
    }

    private int verifyClientScore(String document) {
        return new Random().nextInt(0, 1000);
    }
}
