package LibraryManagementSystem.LibraryPackage.Services;

import LibraryManagementSystem.LibraryPackage.Models.Book;
import LibraryManagementSystem.LibraryPackage.Repositories.BookRepository;

import static org.mockito.Mockito.*;

import LibraryManagementSystem.LibraryPackage.Requests.AddingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingPatronsRequest;
import LibraryManagementSystem.ManagingPackage.Validator.ObjectsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
 public class BookServiceTest {
     @Mock
     private ObjectsValidator<UpdatingBookRequest> updatingBookRequest;
     @Mock
     private ObjectsValidator<AddingBookRequest> addingBookValidator;
     @Mock
     private BookRepository bookRepository;

     @InjectMocks
     private BookService bookService;

     @BeforeEach
     public void setUp() {

         // Initialize mocks
         MockitoAnnotations.openMocks(this);

         // Initialize mock BookRepository with a sample Book object
         Book book = new Book("bookName", 201305, "978-3-16-148443410-0!", "A brief description of the book.");
         when(bookRepository.save(any(Book.class))).thenReturn(book);
         when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
     }

     @Test
     public void getOneBook() {

         Book FakeBook = new Book("bookName", 201305, "978-3-16-148443410-0!", "A brief description of the book.");

         Optional<Book> RealBook = bookRepository.findById(1);

         // Assertions
         assertTrue(RealBook.isPresent());
         assertEquals(FakeBook, RealBook.get());
     }


     @Test
     public void GetAllBooks() {
         Book bookOne = new Book("BookOne", 201305, "978-3-16-148443410-0!", "A brief description of the book.");
         Book bookTwo = new Book("BookTwo", 201305, "978-3-16-148443410-0!", "A brief description of the book.");
         List<Book> FakeBooks = Arrays.asList(bookOne, bookTwo);

         when(bookRepository.findAll()).thenReturn(FakeBooks);
         List<Book> RealBooks = bookRepository.findAll();

         assertNotNull(RealBooks);
         assertEquals(2, RealBooks.size());
         assertEquals(FakeBooks, RealBooks);
     }


     @Test
     public void AddBook() {
         Book FakeBook = new Book("BookName", 201305, "978-3-16-148443410-0!", "A brief description of the book.");
         when(bookRepository.save(FakeBook)).thenReturn(FakeBook);

         AddingBookRequest addingBookRequest=  new AddingBookRequest("BookName", 201305, "978-3-16-148443410-0!", "A brief description of the book.");

         Book RealBook = bookService.AddBook(addingBookRequest);
         doNothing().when(addingBookValidator).validate(addingBookRequest);

         assertTrue(!RealBook.equals(null));
         assertEquals(RealBook, FakeBook);
     }



     @Test
     public void UpdateBook() {
         Book existingBook = new Book("BookName", 201305, "978-3-16-148443410-0!", "A brief description of the book.");
         UpdatingBookRequest updatedBookRequest = new UpdatingBookRequest("UpdatedBookName", 201306, "978-3-16-148443410-0!", "An updated description of the book.");

         // Mocking the behavior of findById and save
         when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
         when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

         // Call the method to test
         Book realBook = bookService.UpdateBook(1, updatedBookRequest);

         // Assertions
         assertNotNull(realBook);
         assertEquals("UpdatedBookName", realBook.getAuthor());
         assertEquals(201306, realBook.getPublicationYear());
         assertEquals("978-3-16-148443410-0!", realBook.getIsbn());
         assertEquals("An updated description of the book.", realBook.getDescription());
     }






 }