package me.reporte.core.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Proposal_seq")
    @SequenceGenerator(name = "Proposal_seq", allocationSize = 1, initialValue = 1, sequenceName = "Proposal_id_seq")
    private Long id;

    private Double requestAmount;

    private int paymentTerm;

    private Boolean approved;

    private Boolean integrated;

    private String observation;

    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) // cascade = CascadeType.ALL para todos
    @JoinColumn(nullable = false)
    @JsonManagedReference // evita mapeamento recursivo
    private User user;

    public Proposal(Long id) {
        this.id = id;
    }
}
