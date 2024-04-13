package br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.michaelalbuquerque.gestao_vagas.modules.company.entities.JobEntity;
import br.com.michaelalbuquerque.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCases {

    @Autowired
    public JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);

    }
    
}
