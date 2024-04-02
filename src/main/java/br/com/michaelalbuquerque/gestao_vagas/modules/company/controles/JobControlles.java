package br.com.michaelalbuquerque.gestao_vagas.modules.company.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michaelalbuquerque.gestao_vagas.modules.company.entities.JobEntity;
import br.com.michaelalbuquerque.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("company/job")
public class JobControlles {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody JobEntity jobEntity){
        return this.createJobUseCase.execute(jobEntity);
    }
}
