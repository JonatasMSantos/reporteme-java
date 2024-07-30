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

    @Mapping(target = "user.firstName", source = "nome")
    @Mapping(target = "user.lastName", source = "sobrenome")
    @Mapping(target = "user.document", source = "cpf")
    @Mapping(target = "user.phoneNumber", source = "telefone")
    @Mapping(target = "user.income", source = "renda")
    @Mapping(target = "requestAmount", source = "valorSolicitado")
    @Mapping(target = "paymentTerm", source = "prazoPagamento")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "integrated", constant = "true")
    @Mapping(target = "observation", ignore = true)
    Proposal convertDTOTOProposal(ProposalRequestDTO req);

    @Mapping(target = "nome", source = "user.firstName")
    @Mapping(target = "sobrenome", source = "user.lastName")
    @Mapping(target = "cpf", source = "user.document")
    @Mapping(target = "telefone", source = "user.phoneNumber")
    @Mapping(target = "renda", source = "user.income")
    @Mapping(target = "valorSolicitado", source = "requestAmount")
    @Mapping(target = "prazoPagamento", source = "paymentTerm")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "aprovado", source = "approved")
    @Mapping(target = "integrado", source = "integrated")
    @Mapping(target = "observacao", source = "observation")
    @Mapping(target = "valorSolicitadoFmt", expression = "java(setRequestAmountToCurrency(proposal))")
    ProposalResponseDTO convertEntityToDTO(Proposal proposal);

    List<ProposalResponseDTO> convertListEntityToListDTO(Iterable<Proposal> proposals);

    default String setRequestAmountToCurrency(Proposal proposal) {
        return NumberFormat.getCurrencyInstance().format(proposal.getRequestAmount());
    }
}
