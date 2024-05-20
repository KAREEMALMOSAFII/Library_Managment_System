package LibraryManagementSystem.LibraryPackage.Controllers;

import LibraryManagementSystem.LibraryPackage.Requests.AddingPatronRequest;
import LibraryManagementSystem.LibraryPackage.Requests.PatronLoginRequest;
import LibraryManagementSystem.LibraryPackage.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/Auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authServic;
    @PostMapping("/Register/patron")
    public ResponseEntity<?> PatronRegister(@RequestBody AddingPatronRequest request)
    {;
        return ResponseEntity.ok( authServic.PatronRegister(request));
    }

    @PostMapping("/Login")
    public ResponseEntity<?>PatronLogin(@RequestBody PatronLoginRequest request)
    {
        return ResponseEntity.ok( authServic.PatronLogin(request));
    }
}
