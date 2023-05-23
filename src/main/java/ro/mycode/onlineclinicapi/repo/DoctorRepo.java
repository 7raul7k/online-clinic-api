package ro.mycode.onlineclinicapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.mycode.onlineclinicapi.models.Clinic;
import ro.mycode.onlineclinicapi.models.Doctor;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    @Query("select d from Doctor d where d.fullName = ?1")
    Optional<Doctor> getDoctorByFullName(String fullName);

    @Query("select d from Doctor d where d.clinic.name = ?1")
    List<Doctor> getDoctorByClinicName(String clinicName);

    @Query("select d from Doctor d where d.email = ?1")
    Optional<Doctor> getDoctorByEmail(String email);

    @Query("select d from Doctor d where d.username = ?1")
    Optional<Doctor> getDoctorByUsername(String username);
}
