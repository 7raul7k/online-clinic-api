package ro.mycode.onlineclinicapi.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Patient;

import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {

    @Query("select p from Patient p where p.fullName = ?1")
    Optional<Patient> getPatientByFullName(String fullName);

    @Query("select p from Patient p where p.email = ?1")
    Optional<Patient> getPatientByEmail(String email);

    @Query("select p from Patient p where p.username = ?1")
    Optional<Patient> getPatientByUsername(String username);

    @Query("select p from Patient p where p.id = ?1")
    Optional<Patient> getPatientById(long id);


}
