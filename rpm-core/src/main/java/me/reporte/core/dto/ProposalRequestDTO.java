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

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String document;

    private Double income;

    private Double requestAmount;

    private int paymentTerm;

}
