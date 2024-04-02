package br.com.michaelalbuquerque.gestao_vagas.exceptions;

import java.util.List;
import java.util.ArrayList;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    
    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource message){
        this.messageSource = message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exceptions){
        List<ErrorMessageDTO> dto = new ArrayList<>();
        exceptions.getBindingResult().getFieldErrors().forEach(erro -> {
            String message = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            ErrorMessageDTO error = new ErrorMessageDTO(message, erro.getField());
            dto.add(error);
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);

    }
}
