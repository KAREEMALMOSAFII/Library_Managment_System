package LibraryManagementSystem.LibraryPackage.Services;

import LibraryManagementSystem.LibraryPackage.Models.Patron;
import LibraryManagementSystem.LibraryPackage.Repositories.PatronRepository;
import LibraryManagementSystem.LibraryPackage.Requests.*;
import LibraryManagementSystem.LibraryPackage.Response.ApiExceptionResponse;
import LibraryManagementSystem.ManagingPackage.Security.Config.JwtService;
import LibraryManagementSystem.ManagingPackage.Security.Token.TokenRepository;
import LibraryManagementSystem.ManagingPackage.Validator.ObjectsValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final ObjectsValidator<AddingPatronRequest> AddindPatronValidator;
    private final PatronRepository patronRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;


    @CachePut(value = "PatronCache")
    public List<Patron> GetAllPatrons() {
        List<Patron> patrons= patronRepository.findAll();
        if(patrons.isEmpty())
            throw new NoSuchElementException("NO Patrons ADDED YET");

        return patrons;
    }
    @CachePut(value = "PatronCache",key = "#id")
    public Patron GetOnePatron(Integer id) {
        Optional<Patron> patron = patronRepository.findById(id);
        if (patron.isEmpty())
            throw new NoSuchElementException("patron NOT FOUND");

        return patron.get();
    }
    @Transactional
    @CachePut(value = "PatronCache")
    public Patron AddPatron(AddingPatronRequest request) {
        AddindPatronValidator.validate(request);
        var patron =Patron.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone_number(request.getPhone_number())
                .address(request.getAddress())
                .build();
        patronRepository.save(patron);
        return  patron;
    }
    @Transactional
    @CachePut(value = "PatronCache")
    public Patron UpdatePatron(Integer id, UpdatingPatronsRequest request) {
        Optional<Patron> patron= Optional.ofNullable(patronRepository.findById(id).orElseThrow(() -> new NoSuchElementException("no such element")));
        if(patron.isEmpty())
            throw new NoSuchElementException(" NO patron FOUND WITH ID "+id);

        var the_Updated_patron =patron.get();

        if (request.getName()!=null)
            the_Updated_patron.setName(request.getName());

        if (request.getAddress()!=null)
            the_Updated_patron.setAddress(request.getAddress());

        if (request.getPhone_number()!=null)
            the_Updated_patron.setPhone_number(request.getPhone_number());



        patronRepository.save(the_Updated_patron);
        return patron.get();
    }
    @Transactional
    @CacheEvict(value = "PatronCache")
    public void DeletePatron(Integer id) {
        Optional <Patron> FindPatron = patronRepository.findById(id);
        if (FindPatron.isEmpty())
            throw new NoSuchElementException("PATRON NOT FOUND");

        var patron=FindPatron.get();
        patronRepository.delete(patron);


    }

}


