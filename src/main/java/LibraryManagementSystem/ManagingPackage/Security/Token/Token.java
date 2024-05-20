package LibraryManagementSystem.ManagingPackage.Security.Token;


import LibraryManagementSystem.LibraryPackage.Models.Patron;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Token {
    @Id
    @GeneratedValue
    public Integer id;
    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType= TokenType.BEARER;

    public boolean revoked;

    public boolean expired;


    @ManyToOne
    @JoinColumn(name = "Patron_ID")
    @JsonBackReference
    public Patron patron;



}
