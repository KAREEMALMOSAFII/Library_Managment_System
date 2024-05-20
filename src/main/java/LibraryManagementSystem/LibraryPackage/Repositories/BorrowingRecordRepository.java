package LibraryManagementSystem.LibraryPackage.Repositories;

import LibraryManagementSystem.LibraryPackage.Models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Integer> {

    Optional<BorrowingRecord> getBorrowingRecordByBook_IdAndPatron_Id (Integer Book_Id, Integer Patron_Id);
}
