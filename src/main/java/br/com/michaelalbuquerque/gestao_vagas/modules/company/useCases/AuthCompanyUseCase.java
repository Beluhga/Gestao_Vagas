package br.com.michaelalbuquerque.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.michaelalbuquerque.gestao_vagas.modules.company.dto.AuthCOmpanyResponseDTO;
import br.com.michaelalbuquerque.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.michaelalbuquerque.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyRepository companyRepository;
    

    public AuthCOmpanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException(("Username or password incorret"));
            });
            var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

            if(!passwordMatches) {
                throw new AuthenticationException();
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var expriesIn = Instant.now().plus(Duration.ofHours(2));

            var token = JWT.create()
            .withIssuer("Alvitre")
            .withExpiresAt(expriesIn)
            .withSubject(company.getId().toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);

            // builder para o token
            var aithCompanyResponseDTO = AuthCOmpanyResponseDTO.builder()
            .access_token(token)
            .expires_in(expriesIn.toEpochMilli())
            .build();

            return aithCompanyResponseDTO;


    }
}


