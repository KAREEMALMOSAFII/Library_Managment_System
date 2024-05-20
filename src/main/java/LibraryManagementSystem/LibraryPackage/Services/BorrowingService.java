package LibraryManagementSystem.LibraryPackage.Services;

import LibraryManagementSystem.LibraryPackage.Models.Book;
import LibraryManagementSystem.LibraryPackage.Models.BorrowingRecord;
import LibraryManagementSystem.LibraryPackage.Models.Patron;
import LibraryManagementSystem.LibraryPackage.Repositories.BookRepository;
import LibraryManagementSystem.LibraryPackage.Repositories.BorrowingRecordRepository;
import LibraryManagementSystem.LibraryPackage.Repositories.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    public ResponseEntity<?> BorrowBook(Integer bookId, Integer patronId) {
        Optional<Book> Findbook = bookRepository.findById(bookId);
        if (Findbook.isEmpty())
            throw new NoSuchElementException("BOOK NOT FOUND IN ID" +bookId);

        Optional<Patron> Findpatron = patronRepository.findById(patronId);
        if (Findpatron.isEmpty())
            throw new NoSuchElementException("PATRON NOT FOUND IN ID" +patronId);

        Book book = Findbook.get();
        Patron patron = Findpatron.get();

        BorrowingRecord borrowingRecord= BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowDate(LocalDate.now())
                .build();
        borrowingRecordRepository.save(borrowingRecord);
        return  ResponseEntity.ok().body("THE BOOK WITH ID "+bookId+" BORRWOED TO "+patron.getName());

    }

    public ResponseEntity<?> UpdateBorrowBook(Integer bookId, Integer patronId) {
        Optional<BorrowingRecord> FindBowrrowedBook = borrowingRecordRepository.getBorrowingRecordByBook_IdAndPatron_Id(bookId,patronId);
        if (FindBowrrowedBook.isEmpty())
            throw new NoSuchElementException("BOOK NOT FOUND IN ID" +bookId);

     BorrowingRecord borrowingRecord= FindBowrrowedBook.get();
     borrowingRecord.setReturnDate(LocalDate.now());

        borrowingRecordRepository.save(borrowingRecord);
        return  ResponseEntity.ok().body("THE BOOK  RETURNED SUCCESSFULLY ");

    }

}
