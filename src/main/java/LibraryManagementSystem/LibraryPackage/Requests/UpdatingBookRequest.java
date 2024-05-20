package LibraryManagementSystem.LibraryPackage.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatingBookRequest {

    private String author;


    private Integer publicationYear;


    private String isbn;


    private String description;


}
