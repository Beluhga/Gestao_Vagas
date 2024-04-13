package br.com.michaelalbuquerque.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {


    public static String objectToJSON(Object object){
        try {

            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
            
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String generateToken(UUID idCompany, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

            var expriesIn = Instant.now().plus(Duration.ofHours(2));

            var token = JWT.create()
            .withIssuer("Alvitre")
            .withExpiresAt(expriesIn)
            .withSubject(idCompany.toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);
            return token;
    }
}
