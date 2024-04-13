package br.com.michaelalbuquerque.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor 
public class AuthCOmpanyResponseDTO {
    
    private String access_token;
    private Long expires_in;
    
}
