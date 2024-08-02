package me.reporte.analysis.service.strategy.impl;

import me.reporte.analysis.service.strategy.PointCalculation;
import me.reporte.core.dto.ProposalResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BankLoansImpl implements PointCalculation {
    @Override
    public int calculate(ProposalResponseDTO proposal) {
        return thereAnotherLoans() ? 0 : 80;
    }

    private boolean thereAnotherLoans() {
        return new Random().nextBoolean();
    }
}
