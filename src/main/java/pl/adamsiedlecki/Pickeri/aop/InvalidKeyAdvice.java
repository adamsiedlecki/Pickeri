package pl.adamsiedlecki.Pickeri.aop;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.adamsiedlecki.Pickeri.exceptions.InvalidKeyException;

@ControllerAdvice
public class InvalidKeyAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String invalidKeyHandler(InvalidKeyException exception) {
        return exception.getMessage();
    }

}
