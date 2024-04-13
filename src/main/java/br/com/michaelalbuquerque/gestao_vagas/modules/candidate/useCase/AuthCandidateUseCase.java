package br.com.michaelalbuquerque.gestao_vagas.modules.candidate.useCase;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.michaelalbuquerque.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

     @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO AuthCandidateRequestDTO) 
    throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(AuthCandidateRequestDTO.username())
        .orElseThrow(() -> {
            throw new UsernameNotFoundException("Login ou Senha incorreto");
        });

        var passwordMatches = this.passwordEncoder
        .matches(AuthCandidateRequestDTO.password(), candidate.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
        .withIssuer("alvitre")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("CANDIDATE")) 
        .withExpiresAt(expiresIn)
        .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();

            return authCandidateResponse;
    }
    
}
