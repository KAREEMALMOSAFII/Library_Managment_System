package LibraryManagementSystem.LibraryPackage.Requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatronLoginRequest {
    private String email;
    private String password;
}
