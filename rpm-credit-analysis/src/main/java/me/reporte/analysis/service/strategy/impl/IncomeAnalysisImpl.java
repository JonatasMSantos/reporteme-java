package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IncomeAnalysisImpl implements PointCalculation {
    @Override
    public int calculate(ProposalResponseDTO proposal) {
        return incomeAnalysis(proposal) ? 100 : 0;
    }

    private boolean incomeAnalysis(ProposalResponseDTO proposal) {
        return Optional.ofNullable(proposal.getIncome()).orElse(0.0) > Optional.ofNullable(proposal.getRequestAmount()).orElse(0.0);
    }
}
