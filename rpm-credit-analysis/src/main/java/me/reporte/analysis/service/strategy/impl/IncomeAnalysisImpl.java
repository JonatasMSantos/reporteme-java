package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;

public class IncomeAnalysisImpl implements PointCalculation {
    @Override
    public int calculate(ProposalResponseDTO proposal) {
        return incomeAnalysis(proposal) ? 100 : 0;
    }

    private boolean incomeAnalysis(ProposalResponseDTO proposal) {
        return proposal.getIncome() > proposal.getRequestAmount();
    }
}
