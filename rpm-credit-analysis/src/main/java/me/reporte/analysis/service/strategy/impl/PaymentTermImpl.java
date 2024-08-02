package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermImpl implements PointCalculation {

    @Override
    public int calculate(ProposalResponseDTO proposal) {
        return proposal.getPaymentTerm() < 120 ? 80 : 0;
    }
}
