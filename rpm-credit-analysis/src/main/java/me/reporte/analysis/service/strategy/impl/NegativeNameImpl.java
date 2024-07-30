package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;

import java.util.Random;

public class NegativeNameImpl implements PointCalculation {

    @Override
    public int calculate(ProposalResponseDTO proposal) {
        boolean clientSituation = verifyClientSituation(proposal.getDocument());
        if (clientSituation) {
            throw new RuntimeException("Client with negative status.");
        }
        return 100;
    }

    private boolean verifyClientSituation(String document) {
        System.out.println(document);
        return new Random().nextBoolean();
    }
}
