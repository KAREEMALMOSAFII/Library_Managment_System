package LibraryManagementSystem.LibraryPackage.Controllers;

import LibraryManagementSystem.LibraryPackage.Services.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> BorrowBook(@PathVariable Integer bookId,@PathVariable Integer patronId)
    {
        return borrowingService.BorrowBook(bookId,patronId);
    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> UpdateBorrowBook(@PathVariable Integer bookId,@PathVariable Integer patronId)
    {
        return borrowingService.UpdateBorrowBook(bookId,patronId);
    }

}
