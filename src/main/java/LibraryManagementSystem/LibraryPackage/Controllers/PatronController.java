package LibraryManagementSystem.LibraryPackage.Controllers;

import LibraryManagementSystem.LibraryPackage.Requests.AddingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.AddingPatronRequest;
import LibraryManagementSystem.LibraryPackage.Requests.PatronLoginRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingPatronsRequest;
import LibraryManagementSystem.LibraryPackage.Services.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    @GetMapping
    public ResponseEntity<?> GetAllPatrons()
    {

        return ResponseEntity.ok( patronService.GetAllPatrons());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> GetOnePatron(@PathVariable Integer id)
    {
        return ResponseEntity.ok( patronService.GetOnePatron(id));
    }

    @PostMapping
    public ResponseEntity<?> AddPatron(@RequestBody AddingPatronRequest request)
    {
        return ResponseEntity.ok( patronService.AddPatron(request));
    }
    @PutMapping("{id}")
    public ResponseEntity<?> UpdatePatron(@PathVariable Integer id,@RequestBody UpdatingPatronsRequest request)
    {
       return ResponseEntity.ok( patronService.UpdatePatron(id,request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> DeletePatron(@PathVariable Integer id)
    {
        patronService.DeletePatron(id);
     return ResponseEntity.ok( ).body("PATRON DELETED SUCCESSFULLY");
    }



}
