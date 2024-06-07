package LibraryManagementSystem.LibraryPackage.Services;

import LibraryManagementSystem.LibraryPackage.Models.Book;
import LibraryManagementSystem.LibraryPackage.Repositories.BookRepository;
import LibraryManagementSystem.LibraryPackage.Requests.AddingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingBookRequest;
import LibraryManagementSystem.LibraryPackage.Response.ApiExceptionResponse;
import LibraryManagementSystem.ManagingPackage.Validator.ObjectsValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BookService {

    private final ObjectsValidator<AddingBookRequest> AddingBookValidator;
    private final BookRepository bookRepository;


  // @CachePut("BookCache")
    public List<Book> GetAllBooks() {
        List<Book>Books= bookRepository.findAll();
        if(Books.isEmpty())
            throw new NoSuchElementException("NO BOOK ADDED YET");

            return Books;
    }
  //  @CachePut(value = "BookCache",key = "#id")
    public Optional<Book> GetOneBook(Integer id) {
       Optional <Book> book = bookRepository.findById(id);
        if (book.isEmpty())
            throw new NoSuchElementException("NO BOOK ADDED YET");

              return book;
    }
    @Transactional
   // @CachePut(value = "BookCache")
    public Book AddBook(AddingBookRequest request) {
        AddingBookValidator.validate(request);
        var book =Book.builder()
                .author(request.getAuthor())
                .publicationYear(request.getPublicationYear())
                .isbn(request.getIsbn())
                .description(request.getDescription())
                .build();
        var savedBook=bookRepository.save(book);

        return savedBook;
    }
    @Transactional
   @CachePut(value = "BookCache")
    public Book UpdateBook(Integer id,UpdatingBookRequest request) {
        Optional<Book> book= Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("THERE IS NO BOOK WITH ID "+ id)));
        if(book.isEmpty())
                throw new NoSuchElementException("THE BOOK NOT FOUND");


        var the_Updated_book =book.get();

        if (request.getAuthor()!=null)
        the_Updated_book.setAuthor(request.getAuthor());

        if (request.getDescription()!=null)
        the_Updated_book.setDescription(request.getDescription());

        if (request.getPublicationYear()!=null)
        the_Updated_book.setPublicationYear(request.getPublicationYear());

        if (request.getIsbn()!=null)
        the_Updated_book.setIsbn(request.getIsbn());



        bookRepository.save(the_Updated_book);
        return the_Updated_book;
    }
    @Transactional
    @CacheEvict(value = "BookCache")
    public ApiExceptionResponse DeleteBook(Integer id) {
        Optional <Book> Findbook = bookRepository.findById(id);

        if (Findbook.isEmpty())
            throw new NoSuchElementException("BOOK NOT FOUND");

        var book=Findbook.get();
        bookRepository.delete(book);

return new ApiExceptionResponse("Book with id "+id+ " Delelted successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }
}
