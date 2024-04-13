package br.com.michaelalbuquerque.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate") 
public class CandidateEntity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;

    @Schema(example = "Fernando Silva", requiredMode = RequiredMode.REQUIRED, description = "Nome candidato")
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    @Schema(example = "Fernando", requiredMode = RequiredMode.REQUIRED, description = "Apelido ou login do candidato")
    private String username;

    @Email(message = "O campo (email) deve conter um e-mail válido")
    @Schema(example = "Fernando@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "Email do candidato")
    private String email;

    @Length(min = 8, max = 100, message = "A senha deve ter no mínimo 8 caracteres")
    @Schema(example = "admin@123", minLength = 8, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;

    @Schema(example = "Desenvolvedor Java", requiredMode = RequiredMode.REQUIRED, description = "breve descrição do candidato")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAT;
}
