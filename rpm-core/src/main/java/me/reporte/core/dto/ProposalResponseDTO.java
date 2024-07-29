package me.reporte.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponseDTO implements Serializable {

    private Long id;

    private String nome;

    private String sobrenome;

    private String telefone;

    private String cpf;

    private String renda;

    private Double valorSolicitado;

    private String valorSolicitadoFmt;

    private int prazoPagamento;

    private Boolean aprovado;

    private Boolean integrado;

    private String observacao;
}
