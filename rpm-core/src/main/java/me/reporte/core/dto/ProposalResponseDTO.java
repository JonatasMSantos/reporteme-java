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

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String document;

    private Double income;

    private Double requestAmount;

    private String requestAmountFmt;

    private int paymentTerm;

    private Boolean approved;

    private Boolean integrated;

    private String observation;
}
