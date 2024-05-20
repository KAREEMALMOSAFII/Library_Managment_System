package LibraryManagementSystem.LibraryPackage.Controllers;

import LibraryManagementSystem.LibraryPackage.Requests.AddingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingBookRequest;
import LibraryManagementSystem.LibraryPackage.Services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController
{
    private final BookService bookService;


    @GetMapping
    public ResponseEntity<?> GetAllBook()
    {
        return ResponseEntity.ok(bookService.GetAllBooks());
    }


    @GetMapping("{id}")
    public ResponseEntity<?> GetOneBook(@PathVariable Integer id)
    {
        return ResponseEntity.ok( bookService.GetOneBook(id));
    }

    @PostMapping
    public ResponseEntity<?> AddBook(@RequestBody AddingBookRequest request)
    {
        return ResponseEntity.ok( bookService.AddBook(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> UpdateBook(@PathVariable Integer id, @RequestBody UpdatingBookRequest request)
    {
        return ResponseEntity.ok( bookService.UpdateBook(id,request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> DeleteBook(@PathVariable Integer id)
    {
        return ResponseEntity.ok( bookService.DeleteBook(id));
    }

}
