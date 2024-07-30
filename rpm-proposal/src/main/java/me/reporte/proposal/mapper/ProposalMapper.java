package me.reporte.proposal.mapper;

import me.reporte.core.entity.Proposal;
import me.reporte.core.dto.ProposalRequestDTO;
import me.reporte.core.dto.ProposalResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;

@Mapper
public interface ProposalMapper {
    ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.document", source = "document")
    @Mapping(target = "user.phoneNumber", source = "phoneNumber")
    @Mapping(target = "user.income", source = "income")
    @Mapping(target = "requestAmount", source = "requestAmount")
    @Mapping(target = "paymentTerm", source = "paymentTerm")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "integrated", constant = "true")
    @Mapping(target = "observation", ignore = true)
    Proposal convertDTOTOProposal(ProposalRequestDTO req);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "document", source = "user.document")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "income", source = "user.income")
    @Mapping(target = "requestAmount", source = "requestAmount")
    @Mapping(target = "paymentTerm", source = "paymentTerm")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "approved", source = "approved")
    @Mapping(target = "integrated", source = "integrated")
    @Mapping(target = "observation", source = "observation")
    @Mapping(target = "requestAmountFmt", expression = "java(setRequestAmountToCurrency(proposal))")
    ProposalResponseDTO convertEntityToDTO(Proposal proposal);

    List<ProposalResponseDTO> convertListEntityToListDTO(Iterable<Proposal> proposals);

    default String setRequestAmountToCurrency(Proposal proposal) {
        return NumberFormat.getCurrencyInstance().format(proposal.getRequestAmount());
    }
}
