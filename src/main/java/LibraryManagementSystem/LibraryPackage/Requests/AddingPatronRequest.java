package LibraryManagementSystem.LibraryPackage.Requests;

import LibraryManagementSystem.ManagingPackage.annotation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddingPatronRequest {

    @NotNull(message = "Patron name cant be null")
    private String name;

    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true)
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    @NotNull(message = "phone_number cant be null")
    private String phone_number;

    @NotNull(message = "address cant be null")
    private String address;

}
