package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;

public class PaymentTermImpl implements PointCalculation {

    @Override
    public int calculate(ProposalResponseDTO proposal) {
        return proposal.getPaymentTerm() < 120 ? 80 : 0;
    }
}
