package me.reporte.analysis.controller;

import lombok.AllArgsConstructor;
import me.reporte.core.dto.ProposalRequestDTO;
import me.reporte.core.dto.ProposalResponseDTO;
import me.reporte.analysis.service.ProposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proposta")
public class ProposalController {

    private ProposalService proposalService;

    @PostMapping
    public ResponseEntity<ProposalResponseDTO> create(@RequestBody ProposalRequestDTO req) {
        ProposalResponseDTO proposalDTO = proposalService.create(req);


        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(proposalDTO.getId())
                        .toUri()
        ).body(proposalDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProposalResponseDTO>> findAll() {
        List<ProposalResponseDTO> values = proposalService.findAll();
        return ResponseEntity.ok(values);
    }
}
