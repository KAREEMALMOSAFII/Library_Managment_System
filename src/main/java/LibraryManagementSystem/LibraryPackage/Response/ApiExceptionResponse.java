package LibraryManagementSystem.LibraryPackage.Response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime localDateTime;

}
