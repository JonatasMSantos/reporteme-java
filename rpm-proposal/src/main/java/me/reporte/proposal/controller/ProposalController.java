package me.reporte.proposal.controller;

import lombok.AllArgsConstructor;
import me.reporte.core.dto.ProposalRequestDTO;
import me.reporte.core.dto.ProposalResponseDTO;
import me.reporte.proposal.service.PendingProposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proposta")
public class ProposalController {

    private PendingProposalService pendingProposalService;

    @PostMapping
    public ResponseEntity<ProposalResponseDTO> create(@RequestBody ProposalRequestDTO req) {
        ProposalResponseDTO proposalDTO = pendingProposalService.create(req);


        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(proposalDTO.getId())
                        .toUri()
        ).body(proposalDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProposalResponseDTO>> findAll() {
        List<ProposalResponseDTO> values = pendingProposalService.findAll();
        return ResponseEntity.ok(values);
    }
}
