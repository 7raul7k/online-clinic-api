package ro.mycode.onlineclinicapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.mycode.onlineclinicapi.models.Admin;

import java.util.List;

public interface AdminRepo extends JpaRepository<Admin,Long> {


    @Query("select a from Admin a where a.email = ?1")
    List<Admin> getAdminByEmail(String email);

    @Query("select a from Admin a")
    List<Admin> getAllAdmin();

    @Query("select a from Admin a where a.fullName = ?1")
    List<Admin> getAdminByFullName(String fullName);

    @Query("select a from Admin a where a.username = ?1")
    List<Admin> getAdminByUsername(String username);

    @Query("select a from Admin a where a.id = ?1")
    List<Admin> getAdminById(long id);
}
