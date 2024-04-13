package br.com.michaelalbuquerque.gestao_vagas.exceptions;

public class UseNotFroundException extends RuntimeException {
    public UseNotFroundException(){
        super("Não encontrado");
    }
}
