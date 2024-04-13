package br.com.michaelalbuquerque.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase.ApplyJobCandidateUseCase;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase.CreateCandidateUserCase;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase.ListAllJobsByFilterUseCases;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase.ProfileCandidateUseCase;
import br.com.michaelalbuquerque.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato" )
public class CandidateController {

   @Autowired 
    private CreateCandidateUserCase createCandidateUserCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCases listAllJobsByFilterUseCases;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsavel por cadastrar um candidato")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Usuário ja Existe")
    })

    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity createCandidateRequest){
        try {
        var result =  this.createCandidateUserCase.execute(createCandidateRequest);
        return ResponseEntity.ok().body(result);

        } catch(Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());

        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsavel por buscar as informações do perfil do candidato")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Informação invalida")
    })
    @SecurityRequirement(name = "jwt_auth")

    public ResponseEntity<Object> get(HttpServletRequest request){

        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase
            .execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);

        } catch(Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());

        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsavel para lista todas as vagas disponiveis baseado no filtro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(
                array = @ArraySchema(
                    schema = @Schema(implementation = JobEntity.class))
            )
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCases.execute(filter);
    }

    
    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga", 
    description = "Essa função é responsavel por realizar a inscrição do candidao a uma vaga")
    @SecurityRequirement(name = "jwt_auth")  
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {

        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);

        } catch(Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());

        }

    }
    
}
