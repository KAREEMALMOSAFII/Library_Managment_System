package LibraryManagementSystem.LibraryPackage.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Getter
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    private String author;

    private Integer publicationYear;

    private String isbn;


    private String description;

    public Book(String author, Integer publicationYear, String isbn, String description) {
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.description = description;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<BorrowingRecord> borrowingRecordList;

}
