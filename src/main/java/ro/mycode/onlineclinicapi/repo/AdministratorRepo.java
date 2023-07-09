package ro.mycode.onlineclinicapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Administrator;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministratorRepo extends JpaRepository<Administrator,Long> {


    @Query("select a from Admin a where a.email = ?1")
    Optional<Administrator> getAdminByEmail(String email);

    @Query("select a from Admin a")
    List<Administrator> getAllAdmin();

    @Query("select a from Admin a where a.fullName = ?1")
    Optional<Administrator> getAdminByFullName(String fullName);

    @Query("select a from Admin a where a.username = ?1")
    Optional<Administrator> getAdminByUsername(String username);

    @Query("select a from Admin a where a.id = ?1")
    Optional<Administrator> getAdminById(long id);
}
