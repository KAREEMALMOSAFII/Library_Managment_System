package LibraryManagementSystem.LibraryPackage.Services;

import LibraryManagementSystem.LibraryPackage.Models.Book;
import LibraryManagementSystem.LibraryPackage.Models.Patron;
import LibraryManagementSystem.LibraryPackage.Repositories.BookRepository;
import LibraryManagementSystem.LibraryPackage.Repositories.PatronRepository;
import LibraryManagementSystem.LibraryPackage.Requests.AddingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.AddingPatronRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingBookRequest;
import LibraryManagementSystem.LibraryPackage.Requests.UpdatingPatronsRequest;
import LibraryManagementSystem.ManagingPackage.Validator.ObjectsValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


class PatronServiceTest {

    @Mock
    private ObjectsValidator<UpdatingPatronsRequest> updatingPatronVaildator;
    @Mock
    private ObjectsValidator<AddingPatronRequest> AddingPatronVaildator;
    @InjectMocks
    private PatronService patronService;

   // @InjectMocks
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize mock BookRepository with a sample Book object
        Patron patron = new Patron("Patron", "john.doe@exampyule.com", "SecurePassword123!", "+1234567890","123 Main Street, Anytown, USA");
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        when(patronRepository.findById(anyInt())).thenReturn(Optional.of(patron));
    }

    @Test
    void getAllPatrons() {
        Patron PatronOne = new Patron("PatronOne", "john.doe@exampyule.com", "SecurePassword123!", "+1234567890","123 Main Street, Anytown, USA");
        Patron PatronTwo = new Patron("PatronTwo", "john.doe@exampyule.com", "SecurePassword123!", "+1234567890","123 Main Street, Anytown, USA");
        List<Patron> FakePatrons = Arrays.asList(PatronOne, PatronTwo);

        when(patronRepository.findAll()).thenReturn(FakePatrons);
        List<Patron> RealPatrons = patronRepository.findAll();

        assertNotNull(RealPatrons);
        assertEquals(2, RealPatrons.size());
        assertEquals(FakePatrons, RealPatrons);
    }

    @Test
    void getOnePatron() {
        Patron FakePatron =  new Patron("Patron", "john.doe@exampyule.com", "SecurePassword123!", "+1234567890","123 Main Street, Anytown, USA");

        Optional<Patron> RealPatron = patronRepository.findById(1);

        // Assertions
        assertTrue(RealPatron.isPresent());
        assertEquals(FakePatron, RealPatron.get());
    }

    @Test
    void addPatron() {
        Patron FakePatron =  new Patron("PatronOne", "john.doe@exampyule.com", passwordEncoder.encode("SecurePassword123!"), "+1234567890","123 Main Street, Anytown, USA");
        when(patronRepository.save(FakePatron)).thenReturn(FakePatron);

        AddingPatronRequest addingPatronRequest=  new AddingPatronRequest("PatronOne", "john.doe@exampyule.com", passwordEncoder.encode( "SecurePassword123!"), "+1234567890","123 Main Street, Anytown, USA");

        Patron RealPatron = patronService.AddPatron(addingPatronRequest);
        doNothing().when(AddingPatronVaildator).validate(addingPatronRequest);

        assertTrue(!RealPatron.equals(null));
        assertEquals(RealPatron, FakePatron);
    }

    @Test
    void updatePatron() {

        Patron existingPatron =  new Patron("PatronOne", "john.doe@exampyule.com", passwordEncoder.encode("SecurePassword123!"), "+1234567890","123 Main Street, Anytown, USA");
        UpdatingPatronsRequest updatedBookRequest = new UpdatingPatronsRequest("UpdatedPatronOne","+1234567890","123 Main Street, Anytown, USA");

        // Mocking the behavior of findById and save
        when(patronRepository.findById(1)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(any(Patron.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method to test
        Patron realPatron = patronService.UpdatePatron(1, updatedBookRequest);

        // Assertions
        assertNotNull(realPatron);
        assertEquals("UpdatedPatronOne", realPatron.getName());
        assertEquals("123 Main Street, Anytown, USA", realPatron.getAddress());
        assertEquals("+1234567890" , realPatron.getPhone_number());

    }
}