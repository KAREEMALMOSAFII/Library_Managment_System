package LibraryManagementSystem.LibraryPackage.Requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddingBookRequest {

    @NotBlank(message = "  author name cant be null ")
    private String author;

    @NotNull(message = "Publication year can't be null")
    private Integer publicationYear;

    @NotBlank(message = "  isbn cant be null ")
    private String isbn;

    @NotBlank(message = "  description cant be null ")
    private String description;


}
