package LibraryManagementSystem.LibraryPackage.Repositories;

import LibraryManagementSystem.LibraryPackage.Models.Book;
import LibraryManagementSystem.LibraryPackage.Models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

}
