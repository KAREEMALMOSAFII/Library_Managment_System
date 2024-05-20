package LibraryManagementSystem.ManagingPackage.Security.Token;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {

    @Query(value = """
    SELECT t FROM Token t 
    INNER JOIN t.patron p ON t.patron.id =:id
    WHERE t.revoked = false AND t.expired = false
""")
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

}
