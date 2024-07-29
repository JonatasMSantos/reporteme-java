package me.reporte.core.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalRequestDTO implements Serializable {

    private String nome;

    private String sobrenome;

    private String telefone;

    private String cpf;

    private String renda;

    private Double valorSolicitado;

    private int prazoPagamento;

}
