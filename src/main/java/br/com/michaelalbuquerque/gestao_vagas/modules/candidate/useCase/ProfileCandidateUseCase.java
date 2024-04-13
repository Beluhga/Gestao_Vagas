package br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.michaelalbuquerque.gestao_vagas.exceptions.UseNotFroundException;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;


@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    
    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
            throw new UseNotFroundException();
        });
        var candidateTDO = ProfileCandidateResponseDTO
        .builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .name(candidate.getName())
        .id(candidate.getId())
        .build();
        return candidateTDO;

    }
}
