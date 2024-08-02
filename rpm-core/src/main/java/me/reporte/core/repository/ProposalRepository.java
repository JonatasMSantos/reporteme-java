package me.reporte.core.repository;

import jakarta.transaction.Transactional;
import me.reporte.core.entity.Proposal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> {

    List<Proposal> findAllByIntegratedIsFalse();

    @Transactional
    @Modifying
    @Query(value = "UPDATE proposal set approved = :approved, observation = :observation WHERE id = :id", nativeQuery = true)
    void updateProposal(Long id, boolean approved, String observation);

}
