package br.com.michaelalbuquerque.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("Usuario não existe");
    }
    
}
