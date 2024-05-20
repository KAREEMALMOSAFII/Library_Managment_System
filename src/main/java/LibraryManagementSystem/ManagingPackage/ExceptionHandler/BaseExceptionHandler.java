package LibraryManagementSystem.ManagingPackage.ExceptionHandler;

import LibraryManagementSystem.LibraryPackage.Response.ApiExceptionResponse;
import LibraryManagementSystem.ManagingPackage.exception.ObjectNotValidException;
import LibraryManagementSystem.ManagingPackage.exception.EmailTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {




    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiExceptionResponse> NullPointerException(NullPointerException  ex) {

        var response = new ApiExceptionResponse(ex.getMessage(),HttpStatus.NO_CONTENT, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ApiExceptionResponse> EmailTakenException(EmailTakenException  ex) {

        var response = new ApiExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> AuthenticationServiceException(BadCredentialsException  ex) {

        var response = new ApiExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        var response = new ApiExceptionResponse(ex.getMessage(),HttpStatus.CONFLICT, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);

    }
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handlevalidationException(ObjectNotValidException ex)
    {
        var response = new ApiExceptionResponse(ex.getErrormessage().toString(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException ex)
    {

        var response = new ApiExceptionResponse(ex.getMessage(),HttpStatus.NOT_FOUND, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiExceptionResponse> handleIllegalStateException(IllegalStateException ex)
    {

        var response = new ApiExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return ResponseEntity.status(response.getStatus()).body(response);


    }

}
