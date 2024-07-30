package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;

import java.util.Random;

public class ScoreNameImpl implements PointCalculation {

    @Override
    public int calculate(ProposalResponseDTO proposal) {
        int clientScore = verifyClientScore(proposal.getDocument());
        if (clientScore <= 200) {
            throw new RuntimeException("Score below 200");
        } else if (clientScore <= 400) {
            return 150;
        } else if (clientScore <= 600) {
            return 180;
        } else {
            return 220;
        }
    }

    private int verifyClientScore(String document) {
        System.out.println(document);
        return new Random().nextInt(0, 1000);
    }
}
