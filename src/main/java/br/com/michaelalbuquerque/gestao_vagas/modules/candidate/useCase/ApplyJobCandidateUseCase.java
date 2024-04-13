package br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.michaelalbuquerque.gestao_vagas.exceptions.JobNotFoundException;
import br.com.michaelalbuquerque.gestao_vagas.exceptions.UseNotFroundException;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.michaelalbuquerque.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;
    
    public ApplyJobEntity execute(UUID idCandidate, UUID idJob){
        this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
            throw new UseNotFroundException();
        });
        this.jobRepository.findById(idJob)
        .orElseThrow(() -> {
            throw new JobNotFoundException();
        });
        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .JobID(idJob).build();

        applyJob = applyJobRepository.save(applyJob);
        return applyJob;
    }
}
