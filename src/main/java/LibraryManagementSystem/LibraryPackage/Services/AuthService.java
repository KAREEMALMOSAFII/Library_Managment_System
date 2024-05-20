package LibraryManagementSystem.LibraryPackage.Services;

import LibraryManagementSystem.LibraryPackage.Models.Patron;
import LibraryManagementSystem.LibraryPackage.Repositories.PatronRepository;
import LibraryManagementSystem.LibraryPackage.Requests.AddingPatronRequest;
import LibraryManagementSystem.LibraryPackage.Requests.PatronLoginRequest;
import LibraryManagementSystem.ManagingPackage.Security.Config.JwtService;
import LibraryManagementSystem.ManagingPackage.Security.Token.AuthenticationResponse;
import LibraryManagementSystem.ManagingPackage.Security.Token.Token;
import LibraryManagementSystem.ManagingPackage.Security.Token.TokenRepository;
import LibraryManagementSystem.ManagingPackage.Security.Token.TokenType;
import LibraryManagementSystem.ManagingPackage.Validator.ObjectsValidator;
import LibraryManagementSystem.ManagingPackage.exception.EmailTakenException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ObjectsValidator<AddingPatronRequest> AddindPatronValidator;
    private final PatronRepository patronRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse PatronRegister(AddingPatronRequest request) {
        AddindPatronValidator.validate(request);

        if (!patronRepository.findByEmail(request.getEmail()).isEmpty())

        throw new EmailTakenException("Email Taken Please Add Another One");

            var patron = Patron.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone_number(request.getPhone_number())
                    .address(request.getAddress())
                    .build();

            var SavePatorn = patronRepository.save(patron);

            String theToken = jwtService.generateToken(patron);


            RevokeAllPatronTokens(SavePatorn);

            SavePatronToken(SavePatorn, theToken);

            var token = AuthenticationResponse.builder()
                    .token(theToken)
                    .build();

            return token;


    }
    @Transactional
    public AuthenticationResponse PatronLogin(PatronLoginRequest request) {

        var patron= patronRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("EMAIL NOT FOUND PLEASE REGISTER"));
        Authentication authentication;
        try {
            authentication=  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));
        } catch (
                AuthenticationException exception) {
            throw new BadCredentialsException("invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Patron SavePatorn= patronRepository.save(patron);
        String jwtToken= jwtService.generateToken(patron);
          RevokeAllPatronTokens(SavePatorn);
         SavePatronToken(SavePatorn,jwtToken);


        var token= AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

        return   token;
    }

    public void SavePatronToken(Patron patron,String jwtToken)
    {


        var  TheToken= Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .patron(patron)
                .build();

        tokenRepository.save(TheToken);

    }


    private void RevokeAllPatronTokens(Patron patron)
    {
        var ValidPatronTokens= tokenRepository.findAllValidTokenByUser(patron.getId());
        if(ValidPatronTokens.isEmpty())
            return;
        ValidPatronTokens.forEach(token ->{
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(ValidPatronTokens);

    }
}
