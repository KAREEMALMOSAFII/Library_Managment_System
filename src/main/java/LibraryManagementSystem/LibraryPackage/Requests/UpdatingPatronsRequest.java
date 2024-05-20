package LibraryManagementSystem.LibraryPackage.Requests;

import LibraryManagementSystem.ManagingPackage.annotation.ValidPassword;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatingPatronsRequest {

    private String name;

    private String phone_number;

    private String address;
}
