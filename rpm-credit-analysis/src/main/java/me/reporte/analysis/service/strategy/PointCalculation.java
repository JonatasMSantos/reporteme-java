package me.reporte.analysis.service.strategy;

import me.reporte.core.dto.ProposalResponseDTO;

public interface PointCalculation {

    int calculate(ProposalResponseDTO proposal);
}
